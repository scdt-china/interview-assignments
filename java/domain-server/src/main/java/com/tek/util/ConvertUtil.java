package com.tek.util;

import java.util.Stack;

public class ConvertUtil {

	private static final char[] ARREAY = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g',
			'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1',
			'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X',
			'C', 'V', 'B', 'N', 'M', '+', '-' };

	/**
	 * @param number long类型的10进制数,该数必须大于0
	 * @return string类型的64进制数
	 */
	public static String encode(long number) {
		long rest = number;
		// 创建栈
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest >= 1) {
			// 进栈,
			// 也可以使用(rest - (rest / 64) * 64)作为求余算法
			stack.add(ARREAY[new Long(rest % 64).intValue()]);
			rest = rest / 64;
		}
		for (; !stack.isEmpty();) {
			// 出栈
			result.append(stack.pop());
		}
		return result.toString();
	}

	/**
	 * 支持范围是A-Z,a-z,0-9,+,-
	 *
	 * @param str 64进制的数字
	 * @return 10进制的数字
	 */
	public static Long decode(String str) {
		// 倍数
		int multiple = 1;
		long result = 0L;
		Character c;
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(str.length() - i - 1);
			result += decodeChar(c) * multiple;
			multiple = multiple * 64;
		}
		return result;
	}

	private static int decodeChar(Character c) {
		for (int i = 0; i < ARREAY.length; i++) {
			if (c == ARREAY[i]) {
				return i;
			}
		}
		return -1;
	}
}
