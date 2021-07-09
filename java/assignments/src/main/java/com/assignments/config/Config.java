package com.assignments.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.assignments.url.cache.UrlCache;

@Configuration
public class Config {

	@Value("${url.total}")
	private int initialCapacity;
	
	/**
	 * 	url缓存
	 * @return
	 */
	@Bean
	public UrlCache url() {
		return new UrlCache(initialCapacity);
	}
}