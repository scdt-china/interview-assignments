package com.example.demo;

import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;

@Service
public class ShortService {

	private volatile int id = -1;
	private final Object idLock = new Object();
	private ConcurrentHashMap<String, String> forwardMap = new ConcurrentHashMap<>();
	private ConcurrentHashMap<String, String> reverseMap = new ConcurrentHashMap<>();
	private final String chars = "t2i8f6g5kl1rypznwm4csa7hb9oxequ30dvj";

	/** short name */
	public String shortName(String longName) {

		if (longName == null || longName.isEmpty()) {
			return "";
		}
		String name = reverseMap.get(longName);
		if (name != null) {
			return name;
		}

		synchronized(idLock) {
			id = (id + 1) % 100000;
		}
		StringBuilder sb = new StringBuilder();
		for (int n = id, i = 0; i < 8; i ++) {
			sb.append(chars.charAt(n % chars.length()));
			n /= chars.length();
		}
		name = sb.toString();
		forwardMap.put(name, longName);
		reverseMap.put(longName, name);
		return name;
	}

	/** long name */
	public String longName(String shortName) {

		if (shortName == null || shortName.isEmpty()) {
			return "";
		}
		return forwardMap.getOrDefault(shortName, "");
	}
}