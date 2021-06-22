package com.rufeng.shorturl.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.rufeng.shorturl.constant.Constants;
import com.rufeng.shorturl.enums.ErrorCode;
import com.rufeng.shorturl.model.Result;
import com.rufeng.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:18 上午
 * @description 短域名服务controller
 */
@RestController
@RequestMapping("/api/url/")
@Api("短域名服务")
public class ShortUrlController {

    @Resource
    private ShortUrlService shortUrlService;


    @SentinelResource(value = Constants.LONG_TO_SHORT_KEY)//, entryType = EntryType.IN
    @GetMapping("longToShort")
    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    public Result<String> longToShort(@RequestParam String longUrl) {
        if (StringUtils.isBlank(longUrl)) {
            return Result.error(ErrorCode.INVALID_PARAM);
        }
        return Result.ok(shortUrlService.longToShort(longUrl));

    }


    @SentinelResource(value = Constants.SHORT_TO_LONG_KEY)
    @GetMapping("shortToLang")
    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    public Result<String> shortToLang(@RequestParam String shortUrl) {
        if (StringUtils.isBlank(shortUrl)) {
            return Result.error(ErrorCode.INVALID_PARAM);
        }
        String longUrl = shortUrlService.shortToLong(shortUrl);
        if (StringUtils.isNotBlank(longUrl)) {
            return Result.ok(longUrl);
        } else {
            return Result.error(ErrorCode.NOT_FOUND);
        }

    }


}
