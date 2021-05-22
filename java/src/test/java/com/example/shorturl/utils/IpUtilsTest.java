package com.example.shorturl.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;

import java.net.InetSocketAddress;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("IP获取工具类测试")
class IpUtilsTest {
	@Test
	@DisplayName("Ipv4地址判断测试")
	void isIpv4Address() {
		assertTrue(IpUtils.isIpv4Address("127.0.0.1"));
		assertTrue(!IpUtils.isIpv4Address("::1"));
		assertTrue(!IpUtils.isIpv4Address("::127.0.0.1"));
		assertTrue(!IpUtils.isIpv4Address("0:0:0:0:0:0:0:1"));
	}

	@Test
	@DisplayName("Ipv6地址判断测试")
	void isIpv6StdAddress() {
		assertTrue(!IpUtils.isIpv6Address("127.0.0.1"));
		assertTrue(IpUtils.isIpv6Address("::1"));
		assertTrue(!IpUtils.isIpv6Address("::127.0.0.1"));
		assertTrue(IpUtils.isIpv6Address("0:0:0:0:0:0:0:1"));
	}

	@Test
	@DisplayName("Ipv6缩写地址判断测试")
	void isIpv6HexCompressedAddress() {
		assertTrue(!IpUtils.isIpv6HexCompressedAddress("127.0.0.1"));
		assertTrue(IpUtils.isIpv6HexCompressedAddress("::1"));
		assertTrue(!IpUtils.isIpv6HexCompressedAddress("::127.0.0.1"));
		assertTrue(!IpUtils.isIpv6HexCompressedAddress("0:0:0:0:0:0:0:1"));
	}

	@Test
	@DisplayName("Ipv6全量地址判断测试")
	void isIpv6Address() {
		assertTrue(!IpUtils.isIpv6Address("127.0.0.1"));
		assertTrue(IpUtils.isIpv6Address("::1"));
		assertTrue(!IpUtils.isIpv6Address("::127.0.0.1"));
		assertTrue(IpUtils.isIpv6Address("0:0:0:0:0:0:0:1"));
	}

	@Test
	@DisplayName("IP地址获取测试")
	void getIp() {
		String[] headNames = new String[]{
				"X-Forwarded-For", "Proxy-Client-IP", "WL-Proxy-Client-IP",
				"HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"
		};
		for (int i = 0; i < 5; i++) {
			ServerHttpRequest request = MockServerHttpRequest.get("")
					.remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
					.header(headNames[i], "111.1.111.1")
					.build();
			assertEquals(IpUtils.getIp(request), "111.1.111.1");
		}

		ServerHttpRequest request1 = MockServerHttpRequest.get("")
				.remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
				.build();
		assertEquals(IpUtils.getIp(request1), "127.0.0.1");
		ServerHttpRequest request2 = MockServerHttpRequest.get("")
				.remoteAddress(new InetSocketAddress("127.0.0.1", 8080))
				.header("X-Forwarded-For", "111.1.111.1,222.2.222.2")
				.build();
		assertEquals(IpUtils.getIp(request2), "111.1.111.1");
		assertEquals(IpUtils.getIp(MockServerHttpRequest.get("").build()), "");
		assertEquals(IpUtils.getIp(null), "");
	}
}
