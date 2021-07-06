package com.example.demo.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.service.UrlService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "短链接接口")
@RestController
public class UrlController {

	@Resource
	private UrlService urlService;

	@ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息")
	@RequestMapping(value = "/toShort", method = {RequestMethod.POST, RequestMethod.GET})
	public String urlTransferToShort(@RequestParam(value = "url", required = true) String normalUrl) {
		return urlService.getShortUrl(normalUrl);
	}

	@ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息")
	@RequestMapping(value = "/toNormal", method = {RequestMethod.POST, RequestMethod.GET})
	public String urlTransferToNormal(@RequestParam(value = "url", required = true) String shortUrl) {
		return urlService.getNormalUrl(shortUrl);
	}

}
