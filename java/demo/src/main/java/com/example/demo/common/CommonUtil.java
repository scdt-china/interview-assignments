package com.example.demo.common;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	public static String createKey(String url) {
		String encodeUrl = urlEncode(url);
		String urlUuid = UUID.nameUUIDFromBytes((encodeUrl).getBytes()).toString();
		return urlUuid.substring(0, 8);
	}

	public static void validateUrlFormat(String url) {
		if (StringUtils.isEmpty(url)) {
			throw new RuleException(Constants.ErrorCode.URL_EMPTY);
		}

		if (!Pattern.matches(Constants.URL_ROLE, url)) {
			throw new RuleException(Constants.ErrorCode.URL_NOT_FORMAT);
		}
	}

	public static void validateShortUrlFormat(String url) {
		validateUrlFormat(url);
		String key = StringUtils.substringAfterLast(url, "/");
		if(StringUtils.indexOf(url, Constants.BASE_DOMAIN) != 0){
			throw new RuleException(Constants.ErrorCode.URL_NOT_FORMAT);
		}
		if (StringUtils.isEmpty(key) || key.length() != 8) {
			throw new RuleException(Constants.ErrorCode.URL_NOT_FORMAT);
		}
	}

	public static String urlEncode(String url) {
		try {
			return URLEncoder.encode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuleException(Constants.ErrorCode.URL_TRANSFER_ERROR);
		}
	}

	public static String urlDecode(String url) {
		try {
			return URLDecoder.decode(url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuleException(Constants.ErrorCode.URL_TRANSFER_ERROR);
		}
	}
}
