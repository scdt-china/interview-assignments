package com.example.shorturlproj.service.impl;

import com.example.shorturlproj.service.ShortUrlTransformService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author lishiyu
 * @description 短域名接口实现类
 */
@Service("shortUrlTransformServiceImpl")
public class ShortUrlTransformServiceImpl  implements ShortUrlTransformService {

    private static final Logger log = LoggerFactory.getLogger(ShortUrlTransformService.class);
    @Autowired
    private Environment environment;

    private static Cache<String,String> transCache= CacheBuilder.newBuilder()
            .maximumSize(10000000)
            .expireAfterAccess(60, TimeUnit.MINUTES)
            .build();

    /**
     *
     * @param url
     * @return 长域名
     */
    @Override
    public String getLongUrlFromShortUrl(String url) {
        String shortUrlPrefix = environment.getProperty("short.url.prefix");
        try{
            //检查域名格式
            log.debug("收到短域名："+url);
            if(url == null || url.length()==0){
                //System.out.println("输入短域名为空");
                throw new Exception("输入短域名为空");
            }
            if(url.lastIndexOf("/")+1 == url.length() || !url.substring(0,url.lastIndexOf("/")+1).equals(shortUrlPrefix))
                throw new Exception("错误的短域名");
            String shortUrl = url.substring(url.lastIndexOf("/")+1);
            String longUrl = transCache.asMap().get(shortUrl);
            if(longUrl == null) {
                log.debug("链接已失效");
                return "链接已失效";
            }
            else
                return longUrl;
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    /**
     *
     * @param longUrl
     * @return 短域名
     */

    @Override
    public String getShortUrlFromLongUrl(String longUrl) {
        try{
            if(longUrl == null || longUrl.length()==0)
            //System.out.println("输入长域名为空");
                throw new Exception("输入长域名为空");

        String shortUrlPrefix = environment.getProperty("short.url.prefix");
        log.debug("收到长域名"+longUrl);
        //for(String iter : transCache.asMap().keySet()){
        //    if(transCache.asMap().get(iter).equals(longUrl))
        //        return shortUrlPrefix+iter;
        //}
        Callable<String> genShortUrl = new GenerateShortUrl<>();
        FutureTask<String> futureTask = new FutureTask<>(genShortUrl);
        new Thread(futureTask).start();
        String shortUrl = futureTask.get();
        transCache.put(shortUrl,longUrl);
        String shortUrlResult = shortUrlPrefix + shortUrl;
        return shortUrlResult;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return e.getMessage();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return e.getMessage();
        }catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }



}

