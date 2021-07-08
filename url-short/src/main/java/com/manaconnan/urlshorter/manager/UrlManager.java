package com.manaconnan.urlshorter.manager;

import com.manaconnan.urlshorter.config.AsynProcessor;
import com.manaconnan.urlshorter.model.BaseResponse;
import com.manaconnan.urlshorter.model.request.UrlRequest;
import com.manaconnan.urlshorter.service.BloomFilterService;
import com.manaconnan.urlshorter.service.ShortUrlService;
import com.manaconnan.urlshorter.utils.ResponseBuilder;
import com.manaconnan.urlshorter.utils.SnowFlake;
import com.manaconnan.urlshorter.utils.Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
@Slf4j
@Component
public class UrlManager {

    private static final String HASH_COLLISION = "@HASH_COLLISION";

    @Autowired
    private ShortUrlService shortUrlService ;
    @Value("${domain}")
    private String DOMAIN;
    @Autowired
    private BloomFilterService bloomFilterService;
    @Autowired
    private AsynProcessor asynProcessor;
    @Autowired
    private SnowFlake idGenerator;

    public BaseResponse<String> convertShortUrl( UrlRequest request){
        String url = request.getUrl();
        if(! Validator.checkUrl(url)){
            log.warn("illegal url = {} ",url);
            return ResponseBuilder.buildFailedResponse("illegal url ,check if start with http,https or ftp");
        }
        log.info("start convert, url = {}",url);
        // step1: check if url might exist ， hash first to get a shorter key
        boolean b = bloomFilterService.mightExist(url);
        String hash = shortUrlService.generateShortUrl(url);
        if (b){
           // might exist, query from local cache and db with hash as key
            String urlByHash = shortUrlService.findByKey(hash);
            if (urlByHash.equals(url)){
                // bingo
                log.info("find from cache");
                return ResponseBuilder.buildSuccessResponse(makeShortUrl(hash));
            }else {
                // hash collision
                url = url+HASH_COLLISION;
                hash = shortUrlService.generateShortUrl(url);
                urlByHash = shortUrlService.findByKey(hash);
                if (!urlByHash.equals(url)){
                    log.error("we got a hash collision problem, url = {}",url);
                    return ResponseBuilder.buildFailedResponse("system error, please try later");
                }
            }
        }

        asynProcessor.add2BloomFilterAndCacheAndDb(hash,url);

        return ResponseBuilder.buildSuccessResponse(makeShortUrl(hash));
    }


    public BaseResponse<String> convertShortUrlSnowFlake( UrlRequest request){
        String url = request.getUrl();
        if(! Validator.checkUrl(url)){
            log.warn("illegal url = {} ",url);
            return ResponseBuilder.buildFailedResponse("illegal url ,check if start with http,https or ftp");
        }
        log.info("start convert, url = {}",url);


        boolean b = bloomFilterService.mightExist(url);
        if (b){
            String val = shortUrlService.findByKey(url);
            if (!StringUtils.isBlank(val)){
                return ResponseBuilder.buildSuccessResponse(makeShortUrl(val));
            }
        }

        long nextId = idGenerator.nextId();
        String  hash = shortUrlService.generateShortUrl(nextId+"");
        asynProcessor.add2BloomFilterAndCacheAndDb(hash,url);
        asynProcessor.add2BloomFilterAndCacheAndDb(url,hash);

        return ResponseBuilder.buildSuccessResponse(makeShortUrl(hash));

    }



    public BaseResponse<String> getOriginUrl(UrlRequest request){

        String shortUrl = request.getUrl();
        if (StringUtils.isBlank(shortUrl) || !shortUrl.startsWith(DOMAIN)){
            return ResponseBuilder.buildFailedResponse("illegal domain");
        }
        String shortcut = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
        if (StringUtils.isBlank(shortcut) || shortcut.length()>8){
            return ResponseBuilder.buildFailedResponse("illegal param");
        }
        log.info("getOriginUrl----->[shortUrl]={}", shortcut);
        String url = shortUrlService.findByKey(shortcut);
        if (StringUtils.isBlank(url)){
            return ResponseBuilder.buildFailedResponse("short url not exist");
        }

        if (url.contains(HASH_COLLISION)){
            url = url.replace(HASH_COLLISION,"");
        }

        log.info("getOriginUrl----->[originUlr]={}", url);

        return ResponseBuilder.buildSuccessResponse(url);
    }

    private String makeShortUrl(String hash){
        return DOMAIN+"/"+hash;
    }

}
