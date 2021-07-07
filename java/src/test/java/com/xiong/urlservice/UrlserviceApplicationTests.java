package com.xiong.urlservice;

import com.alibaba.fastjson.JSONObject;
import com.xiong.urlservice.boot.request.OriginUrlRequest;
import com.xiong.urlservice.boot.request.ShortUrlRequest;
import com.xiong.urlservice.boot.response.Result;
import com.xiong.urlservice.boot.response.ShortUrlResponse;
import com.xiong.urlservice.service.UrlService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest
class UrlserviceApplicationTests {

	@Resource
	private UrlService urlService;


	/**
	 * 生成短链接 测试
	 */
	@Test
	void saveShortUrl() {
		//正常访问 - 预期正常返回短链接
		ShortUrlRequest shortUrlRequest = new ShortUrlRequest();
		String originUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
		shortUrlRequest.setOriginUrl(originUrl);
		Result<ShortUrlResponse> result = urlService.saveShortUrl(shortUrlRequest);
		String shortUrl = result.getData().getShortUrl();
		log.info("生成短链接=原链接【{}】,短链接【{}】", JSONObject.toJSONString(originUrl),JSONObject.toJSONString(shortUrl));

		//重复生成 - 预期返回已生成短链接
		ShortUrlRequest shortUrlRequest2 = new ShortUrlRequest();
		shortUrlRequest2.setOriginUrl(originUrl);
		Result<ShortUrlResponse> result2 = urlService.saveShortUrl(shortUrlRequest2);
		String shortUrl2 = result2.getData().getShortUrl();
		log.info("生成短链接-重复生成=原链接【{}】,短链接【{}】", JSONObject.toJSONString(originUrl),JSONObject.toJSONString(shortUrl2));

	}

	/**
	 * 获取原链接 测试
	 */
	@Test
	void getOriginUrl() {

		ShortUrlRequest shortUrlRequest = new ShortUrlRequest();
		String originUrl = "https://github.com/scdt-china/interview-assignments/tree/master/java";
		shortUrlRequest.setOriginUrl(originUrl);
		Result<ShortUrlResponse> result = urlService.saveShortUrl(shortUrlRequest);
		String shortUrl = result.getData().getShortUrl();
		log.info("生成短链接=原链接【{}】,短链接【{}】", JSONObject.toJSONString(originUrl),JSONObject.toJSONString(shortUrl));

		OriginUrlRequest originUrlRequest = new OriginUrlRequest();
		//测试非本服务的链接
		originUrlRequest.setShortUrl("http://lixi.cn/REN");
		Result<ShortUrlResponse> responseResult = urlService.getOriginUrl(originUrlRequest);
		log.info("获取原链接-测试非本服务的链接 返回结果：{}",JSONObject.toJSONString(responseResult));


		//正常
		originUrlRequest.setShortUrl(shortUrl);
		Result<ShortUrlResponse> responseResult3 = urlService.getOriginUrl(originUrlRequest);
		String originUrl2 = responseResult3.getData().getOriginUrl();
		log.info("获取原链接=原链接【{}】,短链接【{}】", JSONObject.toJSONString(originUrl2),JSONObject.toJSONString(shortUrl));


	}

}
