package com.example.shorturl.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.example.shorturl.configuration.SentinelConfig;
import com.example.shorturl.model.Result;
import com.example.shorturl.service.ShortUrlService;
import com.example.shorturl.utils.CommonUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author hcq
 */
@RestController
@RequestMapping("/api/url/")
@Api(value = "ShortUrlController")
public class ShortUrlController {
	@Autowired
	private ShortUrlService shortUrlService;

	/**
	 * 短域名读取接口：接受短域名信息，返回长域名信息
	 * 使用sentinel限流
	 * @param shortUrl 短域名信息
	 * @return 长域名信息
	 */
	@SentinelResource(SentinelConfig.SHORT_TO_LONG_RES)
	@PostMapping("/stl/{shortUrl}")
	@ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息", response = Result.class)
	public Mono<Result<String>> shortToLang(@PathVariable String shortUrl) {
		if (CommonUtils.isBlank(shortUrl)) {
			return Mono.just(Result.wrapError(Result.EXCEPTION_CODE, "input is blank"));
		}
		try {
			String longUrl = shortUrlService.shortToLong(shortUrl);
			if (longUrl != null) {
				return Mono.just(Result.wrapSuccess(longUrl));
			} else {
				return Mono.just(Result.wrapError(Result.NO_FOUND_CODE, "未找到"));
			}
		} catch (Exception e) {
			return Mono.just(Result.wrapError(Result.EXCEPTION_CODE, e.getMessage()));
		}
	}

	/**
	 * 短域名存储接口：接受长域名信息，返回短域名信息
	 * 使用sentinel限流
	 * @param longUrl 长域名信息
	 * @return 短域名信息
	 */
	@SentinelResource(SentinelConfig.LONG_TO_SHORT_RES)
	@PostMapping("/lts/{longUrl}")
	@ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息", response = Result.class)
	public Mono<Result<String>> longToShort(@PathVariable String longUrl) {
		if (CommonUtils.isBlank(longUrl)) {
			return Mono.just(Result.wrapError(Result.EXCEPTION_CODE, "input is blank"));
		}
		try {
			return Mono.just(Result.wrapSuccess(shortUrlService.longToShort(longUrl)));
		} catch (Exception e) {
			return Mono.just(Result.wrapError(Result.EXCEPTION_CODE, e.getMessage()));
		}
	}
}
