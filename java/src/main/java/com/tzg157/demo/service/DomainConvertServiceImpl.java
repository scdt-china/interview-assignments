package com.tzg157.demo.service;

import com.tzg157.demo.constant.CacheKeyConstant;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.number.IdNumberService;
import com.tzg157.demo.util.HttpSchemaUtil;
import com.tzg157.demo.util.NumericConvertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.Resource;

@Slf4j
@Service
public class DomainConvertServiceImpl implements DomainConvertService{

    @Value("${shortUrlPrefix}")
    private String shortUrlPrefix;

    @Resource(name = "simpleIdNumberService")
    private IdNumberService idNumberService;

    @Resource(name = "caffeineCacheManager")
    private CacheManager cacheManager;

    @Override
    public Response<UrlResult> convertToLong(String url){
        Response<UrlResult> response = new Response<>();
        response.setCode(Response.ResponseCode.FAIL.getCode());
        if(!HttpSchemaUtil.isLegal(url)){
            log.info("url is illegal");
            response.setMessage("url is illegal.");
            return response;
        }
        try {
            Cache cache = cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY);
            Assert.notNull(cache, "Cache must not be null");
            String longUrl = cache.get(url,String.class);
            if (StringUtils.isBlank(longUrl)) {
                log.info("longUrl is blank.");
                response.setMessage("longUrl is blank");
            }else {
                response.setCode(Response.ResponseCode.SUCCESSOR.getCode());
                response.setMessage("OK");
                UrlResult result = new UrlResult(longUrl);
                response.setResult(result);
                log.info("convertToLong shortUrlAppender:{} mapping to longUrl:{}", url, longUrl);
            }
        }catch (Exception e){
            log.error("convertToLong error. msg:{}",e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public Response<UrlResult> convertToShort(String url){
        Response<UrlResult> response = new Response<>();
        response.setCode(Response.ResponseCode.FAIL.getCode());
        if(!HttpSchemaUtil.isLegal(url)){
            response.setMessage("url is illegal");
            log.info("url is illegal");
            return response;
        }

        try {
            Cache cache = cacheManager.getCache(CacheKeyConstant.DOMAIN_CACHE_KEY);
            Assert.notNull(cache, "Cache must not be null");
            Cache.ValueWrapper value = cache.get(url);
            String shortUrl = null;
            if(value != null){
                shortUrl = (String) value.get();
                log.info("get cache value.");
            }else {
                Long id = idNumberService.getNextId();
                String convertParam = NumericConvertUtils.toOtherNumberSystem(id,62);
                if(convertParam.length() > 8){
                    log.info("convertParam:{}",convertParam);
                    throw new Exception("convertParam is greater 8 letters");
                }
                shortUrl = shortUrlPrefix + convertParam;
                cache.putIfAbsent(url,shortUrl);
                cache.putIfAbsent(shortUrl,url);
                log.info("cache has no value,generator shortUrl.");
            }
            response.setCode(Response.ResponseCode.SUCCESSOR.getCode());
            response.setMessage("OK");
            response.setResult(new UrlResult(shortUrl));
            log.info("convertToShort longUrl:{} mapping to shortUrl:{}",url,shortUrl);
        }catch (Exception e){
            log.error("convertToShort error.e:{}, msg:{}",e,e.getMessage());
            response.setMessage(e.getMessage());
        }
        return response;
    }
}
