package com.assignments.url.cache;

import java.util.HashMap;
import java.util.Map;

public class UrlCache {

	/**
	 * 双链
	 * 
	 * @author zxh
	 *
	 */
	class Node {
		String key;
		String value;
		Node before;
		Node after;

		public Node() {
		}

		public Node(String key, String value) {
			this.key = key;
			this.value = value;
		}
	}

	//key :短连接
	private Map<String, Node> cache = new HashMap<String, Node>();
	//key: 长链接
	private Map<String, String> longUrl = new HashMap<String, String>();
	
	private int capacity;
	private Node head, tail;

	public UrlCache(int capacity) {
        this.capacity = capacity;
        head = new Node();
        tail = new Node();
        head.after = tail;
        tail.before = head;
    }

	/**
	 * 	判断长链接是否已映射
	 * @param key
	 * @return
	 */
	public String checkLongUrl(String key) {
		return longUrl.get(key);
	}
	public String get(String key) {
		Node node = cache.get(key);
		if (node == null) {
			return null;
		}
		// 如果 key 存在，先通过哈希表定位，再移到头部
		moveToHead(node);
		return node.value;
	}

	public void put(String key, String value) {
		Node node = cache.get(key);
		if (node == null) {
			Node newNode = new Node(key, value);
			
			cache.put(key, newNode);
			longUrl.put(value, key);
			
			// 添加至双向链表的头部
			addToHead(newNode);
			if (cache.size() > capacity) {
				// 删除尾部节点
				Node tail = removeTail();
				cache.remove(tail.key);
				longUrl.remove(tail.value);
			}
		} else {
			// 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
			node.value = value;
			moveToHead(node);
		}
	}

	private void addToHead(Node node) {
		node.before = head;
		node.after = head.after;
		head.after.before = node;
		head.after = node;
		
	}

	private void removeNode(Node node) {
		node.before.after = node.after;
		node.after.before = node.before;
	}

	private void moveToHead(Node node) {
		removeNode(node);
		addToHead(node);
	}

	private Node removeTail() {
		Node res = tail.before;
		removeNode(res);
		return res;
	}

	public static void main(String[] args) {
		UrlCache map = new UrlCache(2);
		
		map.put("1", "aaaaa");
		map.put("2", "bbbbb");
		map.put("3", "ccccc");
		map.get("2");
		map.put("4", "ddddd");
		System.out.println(map.checkLongUrl("bbbbb"));
		System.out.println(map.checkLongUrl("ddddd"));
	}
}
