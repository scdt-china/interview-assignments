package com.assignments.url.util;

import java.util.concurrent.atomic.AtomicLong;

public class UrlUtils {

	private static final char[] DIGITS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };

	private static AtomicLong sequence = new AtomicLong(0);

	public static String getShortUrl() {
		long seq = sequence.getAndIncrement();
		String shortUrl = to62RadixString(seq);
		return shortUrl;
	}

	private static String to62RadixString(long seq) {
		StringBuilder sBuilder = new StringBuilder();
		while (true) {
			int remainder = (int) (seq % 62);
			sBuilder.append(DIGITS[remainder]);
			seq = seq / 62;
			if (seq == 0) {
				break;
			}
		}
		return sBuilder.toString();
	}
}
