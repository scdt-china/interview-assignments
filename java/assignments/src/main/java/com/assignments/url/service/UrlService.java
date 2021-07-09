package com.assignments.url.service;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.assignments.url.cache.UrlCache;
import com.assignments.url.util.UrlUtils;

@Service
public class UrlService {

	@Autowired
	private UrlCache cache;
	
	private final ReentrantReadWriteLock  lock = new ReentrantReadWriteLock ();
	
	public String create(String longUrl) {
		
		try {
			
			lock.writeLock().lock();
			//判断
			String shortUrl = cache.checkLongUrl(longUrl);
			if(shortUrl != null) {
				return shortUrl;
			}
			
			shortUrl = UrlUtils.getShortUrl();
			cache.put(shortUrl, longUrl);
			return shortUrl;
		} finally {
			lock.writeLock().unlock();
		}
	}
	public String get(String shortUrl) {
		
		try {
			
			lock.readLock().lock();
			String longUrl = cache.get(shortUrl);
			
			return longUrl;
		} finally {
			lock.readLock().unlock();
		}
	}
}
