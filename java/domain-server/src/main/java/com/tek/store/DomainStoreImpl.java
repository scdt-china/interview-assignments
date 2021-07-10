package com.tek.store;

import org.springframework.stereotype.Repository;

import com.tek.cache.LRUCache;


@Repository
public class DomainStoreImpl implements IDomainStore{

	
	private static final LRUCache<String, Long> longDomaiMapNmber = new LRUCache<String, Long>();
	
	private static final LRUCache<Long, String> numberMapLongDomain = new LRUCache<Long, String>();
	
	
	
	@Override
	public synchronized String getDomainByNumber(Long number) {
		
		return numberMapLongDomain.get(number);
	}

	@Override
	public synchronized Long getNmberByDomain(String domain) {
		
		return longDomaiMapNmber.get(domain);
	}

	@Override
	public synchronized void addDomain(String domain, Long number) {
		
		longDomaiMapNmber.put(domain, number);
		numberMapLongDomain.put(number, domain);
		
	}
}
