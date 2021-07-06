package com.example.springbootdemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.common.JsonResult;
import com.util.ShortUrlGenerator;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * controller
 */
@Api("长短url转换api")
@RestController
@RequestMapping("/api")
public class UrlController {

	/**
	 * 通过长域名转换为短域名
	 * 
	 * @param url
	 *            长域名
	 * @return String
	 */
	@ApiOperation("通过长域名转换为短域名")
	@GetMapping("/short-url")
	public JsonResult<Map> shortUrl(@RequestParam String url) {
		Map<String, Object> map = new HashMap<>(3);
		if (StringUtils.isBlank(url) || "null".equals(url)) {
			map.put("shorturl", null);
			return new JsonResult<>(map);
		}
		map.put("shorturl", ShortUrlGenerator.shortUrl(url));
		return new JsonResult<>(map,"获取url成功");
	}

	/**
	 * 通过短域名获取长域名
	 * 
	 * @param shorturl
	 *            短域名
	 * @return String
	 */
	@ApiOperation("通过短域名获取长域名")
	@GetMapping("/long-url")
	public JsonResult<Map> longUrl(@RequestParam String shorturl) {
		Map<String, Object> map = new HashMap<>(3);
		if (StringUtils.isBlank(shorturl) || "null".equals(shorturl)) {
			map.put("longurl", null);
			return new JsonResult<>(map);
		}
		map.put("longurl", ShortUrlGenerator.urlMap.get(shorturl));
		return new JsonResult<>(map,"获取url成功");
	}
}
