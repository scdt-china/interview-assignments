package com.tek.util;

import java.util.concurrent.atomic.AtomicLong;

public class NumberGeneratorUtil {

	private static final AtomicLong DOMAIN_NUMBER = new AtomicLong(0L);
	
	
	public static Long generatorNumber() {
		return DOMAIN_NUMBER.incrementAndGet();
	} 
}
