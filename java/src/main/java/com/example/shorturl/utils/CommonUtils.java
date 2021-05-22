package com.example.shorturl.utils;

/**
 * 一些常量及通用方法
 * @author hcq
 */
public final class CommonUtils {
	private CommonUtils() {
		throw new IllegalStateException("Utility class");
	}
	/**
	 * 模拟数据库map的初始容量
	 */
	public static final int DB_MAP_INIT_CAPACITY = 1024;
	/**
	 * 可以理解为数据库分片数量，这理借map作为数据库存储
	 */
	public static final int URL_DB_SHARED_NUM = 256;
	/**
	 * 错误分片ID
	 */
	public static final int ERROR_SHARED_ID = -1;

	/**
	 * 根据短域名地址10进制值获取分片Id
	 * @param urlId 短域名地址10进制值，用来获取长域名地址
	 * @return URL数据分片的ID
	 */
	public static int getShareIdByUrlId(long urlId) {
		int shareId = (int) (urlId >> SnowFlake.SHARED_ID_SHIFT_COUNT & SnowFlake.MAX_SHARED_ID);
		if (shareId < 0 || shareId >= URL_DB_SHARED_NUM) {
			return ERROR_SHARED_ID;
		}
		return shareId;
	}

	/**
	 * 根据短域名地址获取分片Id
	 * @param shortUrl 用来获取长域名地址
	 * @return URL数据分片的ID
	 */
	public static int getShareIdByShortUrl(String shortUrl) {
		long urlId = ConversionUtil.decode(shortUrl);
		int shareId = (int) (urlId >> SnowFlake.SHARED_ID_SHIFT_COUNT & SnowFlake.MAX_SHARED_ID);
		if (shareId < 0 || shareId >= URL_DB_SHARED_NUM) {
			return ERROR_SHARED_ID;
		}
		return shareId;
	}

	/**
	 * 根据url，计算分片Id
	 * @param longUrl 长域名地址
	 * @return URL数据分片的ID
	 */
	public static int getShareIdByLongUrl(String longUrl) {
		if (longUrl == null) {
			return ERROR_SHARED_ID;
		}
		return longUrl.hashCode() & (URL_DB_SHARED_NUM - 1);
	}

	/**
	 * <p>Checks if a CharSequence is empty (""), null or whitespace only.</p>
	 *
	 * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param cs  the CharSequence to check, may be null
	 * @return {@code true} if the CharSequence is null, empty or whitespace only
	 */
	@SuppressWarnings("checkstyle:InnerAssignment")
	public static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
