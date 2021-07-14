package com.domain.controller;

import com.domain.util.Constants;
import com.domain.util.ShortUrlUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:ShortDomainController
 * @Description:短连接请求接受处理Controller
 * @author: wangkui DJ009697
 * @date:2021/07/12 下午5:04
 * @version:1.0
 */
@RestController
@RequestMapping("/domain")
@Api("ShortDomainController相关的api")
public class ShortDomainController {

    private static final Map<String, String> domainMap = new HashMap();

    /**
     * 长域名转换成短域名
     *
     * @param longUrl 长域名url
     * @return 返回短域名
     */
    @PostMapping("/getShortUrl")
    @ResponseBody
    @ApiOperation(value="获取短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名",  paramType = "query", required = true, dataType =  "String")
    public String getShortUrl(String longUrl) {

        if (ShortUrlUtils.validURL(longUrl)){
            String[] shortUrlArray = ShortUrlUtils.shortUrl(longUrl);

            String shortUrl = Constants.SHORT_URL_DOMAIN + File.separator + shortUrlArray[Constants.INDEX_4-1];

            if (!domainMap.containsKey(shortUrl)) {
                domainMap.put(shortUrl, longUrl);
            }
            return shortUrl;
        }

        return "";
    }

    /**
     * 短域名转换成长域名
     *
     * @param shortUrl 短域名url
     * @throws IOException 抛出的异常
     * @return 返回长域名
     */
    @PostMapping(value = "/getLongUrl")
    @ResponseBody
    @ApiOperation(value="获取长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名",  paramType = "query", required = true, dataType =  "String")
    public String getLongUrl(String shortUrl) throws IOException {

        return domainMap.get(shortUrl);
    }
}
