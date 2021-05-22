package com.example.shorturl.service.impl;

import com.example.shorturl.repository.UrlRepository;
import com.example.shorturl.service.IdService;
import com.example.shorturl.service.ShortUrlService;
import com.example.shorturl.utils.CommonUtils;
import com.example.shorturl.utils.ConversionUtil;
import com.google.common.base.Charsets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * 短域名Service实现
 * @author hcq
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {
	/**
	 * 短域名URL前缀
	 */
	public static final String SHORT_DOMAIN_PREFIX = "http://localhost:8080/";
	/**
	 * 布隆过滤器 期望插入的元素总个数
	 */
	private static final int BLOOM_FILTER_INSERTIONS = 1000000;
	/**
	 * 布隆过滤器 期望的假阳性率
	 */
	private static final double BOOLE_FILTER_FPP = 0.001;

	@Autowired
	private UrlRepository urlRepository;
	@Autowired
	private IdService idService;

	/**
	 * 初始化一个存储string数据的布隆过滤器，初始化大小100w，用来过滤不存在短域名URL
	 */
	private BloomFilter bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8),
			BLOOM_FILTER_INSERTIONS, BOOLE_FILTER_FPP);

	/**
	 * 长域名URL转短域名URL
	 * @param longUrl 长域名URL
	 * @return 短域名URL
	 */
	@Override
	@CacheEvict(value = "URL", key = "#result")
	public String longToShort(String longUrl) {
		String shortUrl = urlRepository.getShortUrl(longUrl);
		if (shortUrl == null) {
			int shareId = CommonUtils.getShareIdByLongUrl(longUrl);
			long id = idService.genId(shareId);
			shortUrl = ConversionUtil.encode(id);
			//并发请求相同时，有可能已经有这个地址了，重复返回原来shortUrl
			shortUrl = urlRepository.putUrl(shareId, shortUrl, longUrl);
			//增加到布隆过滤器，用来过滤重复的长滤名转换申请
			bloomFilter.put(shortUrl);
		}
		return SHORT_DOMAIN_PREFIX + shortUrl;
	}

	/**
	 * 短域名URL
	 * @param shortUrl 短域名URL
	 * @return 长域名URL
	 */
	@Override
	@Cacheable(value = "URL", key = "#shortUrl", unless = "#result==null")
	public String shortToLong(String shortUrl) {
		if (!shortUrl.startsWith(SHORT_DOMAIN_PREFIX)) {
			return null;
		}
		shortUrl = shortUrl.substring(SHORT_DOMAIN_PREFIX.length());
		//过滤掉大部分不存在的短域名URL
		if (!bloomFilter.mightContain(shortUrl)) {
			return null;
		}
		return urlRepository.getLongUrl(shortUrl);
	}
}
