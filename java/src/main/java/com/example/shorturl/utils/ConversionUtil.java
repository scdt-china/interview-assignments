package com.example.shorturl.utils;

/**
 * 10进制 62进制 转换
 * @author hcq
 */
public final class ConversionUtil {
	private ConversionUtil() {
		throw new IllegalStateException("Utility class");
	}
	/**
	 * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61等
	 */
	private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	/**
	 * 进制数
	 */
	private static final int SCALE = 62;
	/**
	 * 错误结果
	 */
	public static final int ERROR_RESULT = -1;

	/**
	 * 将数字转为62进制
	 * @param num    Long型数字(不能是负数)
	 * @return 62进制字符串
	 */
	public static String encode(long num) {
		if (num < 0) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		int remainder = 0;

		while (num > SCALE - 1) {
			remainder = Long.valueOf(num % SCALE).intValue();
			sb.append(CHARS.charAt(remainder));
			num = num / SCALE;
		}

		sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
		return sb.reverse().toString();
	}

	/**
	 * 62进制字符串转为数字
	 * @param str 编码后的62进制字符串
	 * @return 解码后的 10 进制字符串
	 */
	public static long decode(String str) {
		if (str == null) {
			return ERROR_RESULT;
		}
		long num = 0;
		int index = 0;
		for (int i = 0; i < str.length(); i++) {
			index = CHARS.indexOf(str.charAt(i));
			if (index == ERROR_RESULT) {
				return ERROR_RESULT;
			}
			num += (long) (index * (Math.pow(SCALE, str.length() - i - 1)));
		}

		return num;
	}
}
