package com.tek.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LRUCache<K, V> {

	/**
	 * 容量
	 */
	private int capacity = 500;
	/**
	 * Node记录表
	 */
	private Map<K, Node<K, V>> table = new ConcurrentHashMap<>();
	/**
	 * 双向链表头部
	 */
	private Node<K, V> head;
	/**
	 * 双向链表尾部
	 */
	private Node<K, V> tail;

	public LRUCache(int capacity) {
		this();
		this.capacity = capacity;
	}

	public LRUCache() {
		head = new Node<>();
		tail = new Node<>();
		head.next = tail;
		head.prev = null;
		tail.prev = head;
		tail.next = null;
	}

	public V get(K key) {

		Node<K, V> node = table.get(key);
		if (node == null) {
			return null;
		}
		//移除node节点
		node.prev.next = node.next;
		node.next.prev = node.prev;

		// 移动node节点到表头
		node.next = head.next;
		head.next.prev = node;
		
		node.prev = head;
		head.next = node;
		// 存在缓存表
		table.put(key, node);
		return node.value;
	}

	public void put(K key, V value) {
		Node<K, V> node = table.get(key);
		// 如果Node不在表中，代表缓存中并没有
		if (node == null) {
			if (table.size() == capacity) {
				// 超过容量了 ,首先移除尾部的节点
				table.remove(tail.prev.key);
				tail.prev.prev.next = tail;
				tail.prev = tail.prev.prev;
			}
			node = new Node<>();
			node.key = key;
			node.value = value;
			table.put(key, node);
		}
		//移动Node节点到表头
		node.next = head.next;
		head.next.prev = node;
		node.prev = head;
		head.next = node;
	}

	/**
	 * 双向链表内部类
	 */
	private static class Node<K, V> {
		private K key;
		private V value;
		private Node<K, V> prev;
		private Node<K, V> next;

		private Node(K key, V value) {
			this.key = key;
			this.value = value;
		}

		public Node() {

		}
	}
}