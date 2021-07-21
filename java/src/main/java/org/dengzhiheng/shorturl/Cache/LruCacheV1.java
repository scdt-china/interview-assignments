package org.dengzhiheng.shorturl.Cache;


import lombok.extern.slf4j.Slf4j;
import org.dengzhiheng.shorturl.utils.RamMonitor;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * LruCache实现，线程安全
 * @author : When6passBye
 * @date : 2021/7/19 下午5:12
 */
@Slf4j
public class LruCacheV1<K, V> {
    private final int maxCapacity;
    private final Map<K, Node<K, V>> map;
    private Node<K, V> head, tail;

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock writeLock = lock.writeLock();
    private final Lock readLock = lock.readLock();

    private static class Node<K, V> {
        private V value;
        private final K key;
        private Node<K, V> next, prev;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    // 从双向链表中移除一个节点
    private void removeNode(Node<K, V> node) {
        if (node == null) return;

        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        } else {
            tail = node.prev;
        }
    }

    // 向双向链表的尾部添加一个节点
    private void offerNode(Node<K, V> node) {
        if (node == null) return;

        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            node.next = null;
            tail = node;
        }
    }

    public LruCacheV1(final int maxCapacity) {
        this.maxCapacity = maxCapacity;
        map = new HashMap<>();
    }

    public void put(K key, V value) {
        writeLock.lock();
        try {
            if (map.containsKey(key)) {
                Node<K, V> node = map.get(key);
                node.value = value;
                removeNode(node);
                offerNode(node);
            } else {
                //检查内存
                if(RamMonitor.isThreshold()||map.size() >= maxCapacity){
                    log.info("ram arrive threshold =====> [head.key]={}", head.key);
                    map.remove(head.key);
                    removeNode(head);
                }
                Node<K, V> node = new Node<>(key, value);
                offerNode(node);
                map.put(key, node);
            }
        } finally {
            writeLock.unlock();
        }
    }

    public V get(K key) {
        writeLock.lock();
        try {
            Node<K, V> node = map.get(key);
            if (node == null) return null;
            removeNode(node);
            offerNode(node);
            return node.value;
        } finally {
            writeLock.unlock();
        }
    }

    public int size() {
        readLock.lock();
        try {
            return map.size();
        } finally {
            readLock.unlock();
        }
    }
}
