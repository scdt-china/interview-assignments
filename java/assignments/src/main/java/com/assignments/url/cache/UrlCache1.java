package com.assignments.url.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class UrlCache1 extends LinkedHashMap<String, String> {

	private static final long serialVersionUID = -7500378543108575777L;

	private int initialCapacity;

	public UrlCache1(int initialCapacity) {
		super(initialCapacity, 0.75F, true);
		this.initialCapacity = initialCapacity;
	}

	@Override
	protected boolean removeEldestEntry(Map.Entry<String, String> eldest) {
		return size() > initialCapacity;
	}
	

	public static void main(String[] args) {
		UrlCache1 map = new UrlCache1(2);
		map.put("1", "aaaaa");
		map.put("2", "bbbbb");
		map.put("3", "ccccc");
		map.get("2");
		System.out.println();
	}
}
