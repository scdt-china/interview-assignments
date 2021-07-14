package com.thuangster.urlshortener.services;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.thuangster.urlshortener.model.UrlMap;

@Service
public class UrlshortenerService {
	
	private static final int CACHE_SIZE = 1000000;
	private static final String CHAR_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	private Map<String, String> shortToLong = new UrlMap<>(100, 0.75f, true, CACHE_SIZE);
	private Map<String, String> longToShort = new UrlMap<>(100, 0.75f, true, CACHE_SIZE);

	public String encode(String longUrl) {
		if (longToShort.containsKey(longUrl)) {
			return longToShort.get(longUrl);
		}
		boolean codeGenerated = false;
		while (!codeGenerated) {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 8; i++) {
				int codeIdx = (int) (Math.random() * CHAR_SET.length());
				sb.append(CHAR_SET.charAt(codeIdx));
			}
			if (!shortToLong.containsKey(sb.toString())) {
				shortToLong.put(sb.toString(), longUrl);
				longToShort.put(longUrl, sb.toString());
				sb.setLength(0);
				codeGenerated = true;
			}
		}
		return longToShort.get(longUrl);
	}

	public String decode(String shortUrl) {
		return shortToLong.get(shortUrl);
	}

}
