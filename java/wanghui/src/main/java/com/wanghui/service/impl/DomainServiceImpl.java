package com.wanghui.service.impl;

import com.wanghui.model.ShortUrl;
import com.wanghui.service.DomainService;
import com.wanghui.service.RepositoryService;
import com.wanghui.utils.CommonConstants;
import com.wanghui.utils.IDMaker;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wanghui
 * @title 域名处理业务逻辑实现类
 * @Date 2021-07-17 22:30
 * @Description
 */
@Slf4j
@Service
public class DomainServiceImpl implements DomainService {

    @Autowired
    private RepositoryService repositoryService;

    // 网址二级域名提取
    private static final Pattern p = Pattern.compile("(?<=http://|\\.)[^.]*?\\.(com|cn|net|org|biz|info|cc|tv)",Pattern.CASE_INSENSITIVE);


    @Cacheable(cacheNames = {"shortUrl"},key = "#shortUrl")
    @Override
    public ShortUrl short2LongUrl(String shortUrl) {

        log.info("通过短链接获取长链接,入参:{}", shortUrl);
        String longUrl = repositoryService.getLongUrlByShortUrl(shortUrl);
        return ShortUrl.builder().code(longUrl != null ? CommonConstants.CODE_SUCCESS : CommonConstants.CODE_LONG_URL_NOT_FOUND)
                .shortUrl(shortUrl)
                .longUrl(longUrl).msg(longUrl != null ? "获取到长域名" : "没有找到对应的长域名").build();
    }



    @Override
    public ShortUrl long2ShortUrl(String longUrl) {
        String shortUrl = repositoryService.getShortUrlByLongUrl(longUrl);
        if(shortUrl !=null) {
            return ShortUrl.builder().code(CommonConstants.CODE_SUCCESS)
                    .shortUrl(shortUrl).longUrl(longUrl).msg("获取到短域名").build();
        }

        //生成短域名
        IDMaker idMaker = new IDMaker();
        idMaker.init();
        Matcher m = p.matcher(longUrl);
        if(!m.find()){
            throw new RuntimeException("长域名不合法");
        }
        String secondLevelDomain = m.group();
        String nodeName = "/" + secondLevelDomain + "/ID-";
        shortUrl = secondLevelDomain + "/" +idMaker.makeId(nodeName);
        idMaker.destroy();
        try{
            repositoryService.saveShorAndLongtUrl(shortUrl,longUrl);
        }catch (Exception ex){
            log.error("长域名转换短域名发生异常");
            return ShortUrl.builder().code(CommonConstants.CODE_SERVICE_UNAVAILABLE)
                    .shortUrl(shortUrl).longUrl(longUrl)
                    .msg(ex.getMessage()).build();
        }
        return ShortUrl.builder().code(CommonConstants.CODE_SUCCESS)
                .shortUrl(shortUrl).longUrl(longUrl)
                .msg("长域名已被转换为短域名，且持久化").build();
    }
}
