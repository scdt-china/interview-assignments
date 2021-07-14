package com.thuangster.urlshortener.model;

import java.util.LinkedHashMap;

public class UrlMap<K, V> extends LinkedHashMap<K, V> {

	private static final long serialVersionUID = 1L;

	private int maxSize = 1000000;

	public UrlMap(int initialCapacity, float loadFactor, boolean accessOrder,
			int maxSize) {
		super(initialCapacity, loadFactor, accessOrder);
		setMaxSize(maxSize);
	}

	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<K, V> eldest) {
		return this.size() > maxSize;
	}

	public void setMaxSize(int maxSize) {
		this.maxSize = maxSize;
	}
}
