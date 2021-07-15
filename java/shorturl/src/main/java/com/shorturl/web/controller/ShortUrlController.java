
package com.shorturl.web.controller;

import com.shorturl.common.*;
import com.shorturl.common.MyResponse;
import com.shorturl.service.ShortUrlService;
import io.swagger.annotations.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 在这里编写类的功能描述
 *
 * @author penghang
 * @created 7/8/21
 */
@Api(value = "短链接服务接口", tags = "短链接服务接口")
@RestController
@RequestMapping("/api/url")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;

    @GetMapping(value = "short/get")
    @ApiOperation(value = "getShortUrl", httpMethod = "GET", response = MyResponse.class, notes = "长链接置换短链接")
    public MyResponse<String> getShortUrl(@ApiParam(name = "originalUrl", required = true) String originalUrl) {
        if (!StringUtils.hasText(originalUrl)) {
            throw new BusinessException(CommonErrorCodeEnum.PARAM_ERROR);
        }
        String shortUrl = shortUrlService.getShortUrl(originalUrl);
        return MyResponse.buildSuccess(shortUrl);
    }

    @GetMapping(value = "original/get")
    @ApiOperation(value = "getOriginalUrl", httpMethod = "GET", response = MyResponse.class, notes = "短链接置换长链接")
    public MyResponse<String> getOriginalUrl(@ApiParam(name = "shortUrl", required = true) String shortUrl) {
        if (!StringUtils.hasText(shortUrl)) {
            throw new BusinessException(CommonErrorCodeEnum.PARAM_ERROR);
        }
        String originalUrl = shortUrlService.getOriginalUrl(shortUrl);
        return MyResponse.buildSuccess(originalUrl);
    }

}