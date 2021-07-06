package com.example.springbootdemo.test;

import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.util.HttpUtil;

@RunWith(SpringRunner.class)
public class UrlTest {
	@Test
	public void testGetShortUrl() throws Exception{
		String url = "http://127.0.0.1:8886/api/short-url";
		HashMap<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("url", "http://m.news.cctv.com/2021/06/23/ARTI8Ojuv56kOQMFBdS0X2F9210623.shtml");
//		try {
			String shorturl = HttpUtil.get(url, paramMap);
			System.out.println(String.format("shorturl:%s", shorturl));
//		} catch (Exception e) {
//			System.out.println(String.format("调用接口异常:%s", e.getMessage()));
//		}
	}
	
	@Test
	public void testGetUrl()throws Exception {
		String url = "http://127.0.0.1:8886/api/long-url";
		HashMap<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("shorturl", "http://Y7JJ3y");
//		try {
			String shorturl = HttpUtil.get(url, paramMap);
			System.out.println(String.format("url:%s", shorturl));
//		} catch (Exception e) {
//			System.out.println(String.format("调用接口异常:%s", e.getMessage()));
//		}
		
	}
	
}
