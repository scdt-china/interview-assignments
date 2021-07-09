
package com.assignments.url.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.assignments.url.dto.UrlDTO;
import com.assignments.url.service.UrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 短域名服务接口
 * 
 * @author zxh
 *
 */
@Api(tags = "短域名服务接口")
@RestController
@RequestMapping("/url")
public class UrlController {

	@Autowired
	private UrlService service;
	/**
	 * 	短域名存储接口：接受长域名信息，返回短域名信息
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "短域名存储接口", notes = "短域名存储接口")
	@PutMapping
	public String create(@RequestBody UrlDTO dto) {

		String shortUrl = service.create(dto.getLongUrl());
		
		return shortUrl;
	}

	/**
	 * 	短域名读取接口：接受短域名信息，返回长域名信息。
	 * @param shortUrl
	 * @return
	 */
	@ApiOperation(value = "短域名读取接口", notes = "短域名读取接口")
	@GetMapping("/{shortUrl}")
	public String get(@PathVariable("shortUrl") String shortUrl) {

		
		String longUrl = service.get(shortUrl);
		
		if(longUrl != null && longUrl.length() > 0) {
			return longUrl;
		}else {
			return "短域名不存在！";
		}

	}
}
