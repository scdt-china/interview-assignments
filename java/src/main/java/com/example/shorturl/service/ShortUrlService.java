package com.example.shorturl.service;

/**
 * 短域名 Service
 * @author hcq
 */
public interface ShortUrlService {
	/**
	 * 长域名URL转短域名URL
	 * @param longUrl 长域名URL
	 * @return 短域名URL
	 */
	String longToShort(String longUrl);

	/**
	 * 短域名URL
	 * @param shortUrl 短域名URL
	 * @return 长域名URL
	 */
	String shortToLong(String shortUrl);
}
