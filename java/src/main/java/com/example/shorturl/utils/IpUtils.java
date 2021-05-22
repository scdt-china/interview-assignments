package com.example.shorturl.utils;


import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.net.InetSocketAddress;
import java.util.regex.Pattern;

/**
 * IP获取工具
 * @author hcq
 */
@SuppressWarnings({"PMD.UndefineMagicConstantRule"})
public final class IpUtils {
	private IpUtils() {

	}

	private static final int IP_MIN_SPLIT_LENGTH = 15;

	private static final Pattern IPV4_PATTERN =
			Pattern.compile("^(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)(\\.(25[0-5]|2[0-4]\\d|[0-1]?\\d?\\d)){3}$");

	private static final Pattern IPV6_STD_PATTERN =
			Pattern.compile("^(?:[0-9a-fA-F]{1,4}:){7}[0-9a-fA-F]{1,4}$");

	private static final Pattern IPV6_HEX_COMPRESSED_PATTERN = Pattern
			.compile("^((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)::"
					+ "((?:[0-9A-Fa-f]{1,4}(?::[0-9A-Fa-f]{1,4})*)?)$");

	/**
	 * isIpv4Address
	 *
	 * @param input input
	 * @return boolean
	 */
	public static boolean isIpv4Address(final String input) {
		return IPV4_PATTERN.matcher(input).matches();
	}

	/**
	 * isIpv6StdAddress
	 *
	 * @param input input
	 * @return boolean
	 */
	public static boolean isIpv6StdAddress(final String input) {
		return IPV6_STD_PATTERN.matcher(input).matches();
	}

	/**
	 * isIpv6HexCompressedAddress
	 *
	 * @param input input
	 * @return boolean
	 */
	public static boolean isIpv6HexCompressedAddress(final String input) {
		return IPV6_HEX_COMPRESSED_PATTERN.matcher(input).matches();
	}

	/**
	 * isIpv6Address
	 *
	 * @param input input
	 * @return boolean
	 */
	public static boolean isIpv6Address(final String input) {
		return isIpv6StdAddress(input) || isIpv6HexCompressedAddress(input);
	}

	/**
	 * 获取用户IP
	 * @param request httpRequest
	 * @return ip地址
	 */
	public static String getIp(ServerHttpRequest request) {
		if (request == null) {
			return "";
		}
		HttpHeaders headers = request.getHeaders();
		String ip = headers.getFirst("X-Forwarded-For");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = headers.getFirst("Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = headers.getFirst("WL-Proxy-Client-IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = headers.getFirst("HTTP_CLIENT_IP");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
			}
			if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
				InetSocketAddress address = request.getRemoteAddress();
				if (address != null) {
					ip = address.getAddress().getHostAddress();
				}
			}
		} else if (ip.length() > IP_MIN_SPLIT_LENGTH) {
			String[] ips = ip.split(",");
			for (int index = 0; index < ips.length; index++) {
				String strIp = ips[index];
				if (!("unknown".equalsIgnoreCase(strIp))) {
					ip = strIp;
					break;
				}
			}
		}
		if (CommonUtils.isBlank(ip)) {
			return "";
		}
		if (isIpv4Address(ip) || isIpv6Address(ip)) {
			return ip;
		}
		return "";
	}
}
