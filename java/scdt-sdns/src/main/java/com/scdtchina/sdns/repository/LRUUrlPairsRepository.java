package com.scdtchina.sdns.repository;

import com.scdtchina.sdns.po.UrlPair;
import com.scdtchina.sdns.util.CacheNode;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class LRUUrlPairsRepository {

    private int capacity;
    private int size;
    private CacheNode<UrlPair> head;
    private CacheNode<UrlPair> tail;
    private volatile Map<String, CacheNode<UrlPair>> shortUrlMapping;
    private volatile Map<String, CacheNode<UrlPair>> normalUrlMapping;

    private ReentrantLock lock = new ReentrantLock();


    public LRUUrlPairsRepository(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("UrlPairs capacity must be greater than zero");
        }
        this.capacity = capacity;
        this.size = 0;
        shortUrlMapping = new HashMap<>(capacity);
        normalUrlMapping = new HashMap<>(capacity);
    }

    public String addUrlPair(String normalUrl, String shortUrl) {
        String repoShortUrl = findByNormalUrl(normalUrl);
        if (StringUtils.isNotEmpty(repoShortUrl)) {
            return repoShortUrl;
        }
        lock.lock();
        try {
            if (size >= capacity) {
                removeLast();
            }
            UrlPair urlPair = new UrlPair(normalUrl, shortUrl);
            CacheNode<UrlPair> node = new CacheNode<>(urlPair);
            shortUrlMapping.put(shortUrl, node);
            normalUrlMapping.put(normalUrl, node);
            moveToHead(node);
            size++;
            return shortUrl;
        } finally {
            lock.unlock();
        }
    }

    public String findByNormalUrl(String normalUrl) {
        CacheNode<UrlPair> node = normalUrlMapping.get(normalUrl);
        if (node != null) {
            moveToHead(node);
            return node.getVal().getShortUrl();
        } else {
            return null;
        }
    }

    public String findByShortUrl(String shortUrl) {
        CacheNode<UrlPair> node = shortUrlMapping.get(shortUrl);
        if (node != null) {
            moveToHead(node);
            return node.getVal().getNormalUrl();
        } else {
            return null;
        }

    }

    private void moveToHead(CacheNode node) {
        lock.lock();
        try {
            if (node == head) {
                return;
            }
            if (node == tail) {
                tail = node.getPrev();
            }
            if (node.getPrev() != null) {
                node.getPrev().setNext(node.getNext());
            }
            if (node.getNext() != null) {
                node.getNext().setPrev(node.getPrev());
            }
            node.setPrev(null);
            node.setNext(null);

            if (head == null) {
                head = node;
                tail = node;
            } else {
                node.setNext(head);
                head.setPrev(node);
                head = node;
            }
        } finally {
            lock.unlock();
        }
    }

    private void removeLast() {
        lock.lock();
        try {
            if (tail != null) {

                normalUrlMapping.remove(tail.getVal().getNormalUrl());
                shortUrlMapping.remove(tail.getVal().getShortUrl());

                if (head == tail) {
                    head = null;
                    tail = null;
                } else {
                    CacheNode prev = tail.getPrev();

                    if (tail.getPrev() != null) {
                        tail.getPrev().setNext(tail.getNext());
                    }
                    if (tail.getNext() != null) {
                        tail.getNext().setPrev(tail.getPrev());
                    }
                    tail.setPrev(null);
                    tail.setNext(null);
                    tail = prev;
                }
                size--;
            }

        } finally {
            lock.unlock();
        }
    }

}
