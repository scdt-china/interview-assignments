package com.homework.tinyurl.web;

import com.homework.tinyurl.enums.TinyUrlExceptionCodeEnum;
import com.homework.tinyurl.model.common.Result;
import com.homework.tinyurl.model.exception.TinyUrlException;
import com.homework.tinyurl.model.web.request.CreateTinyUrlRequest;
import com.homework.tinyurl.model.web.request.GetOriginalUrlRequest;
import com.homework.tinyurl.service.TinyUrlCoreService;
import com.homework.tinyurl.utils.UrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/17 8:45 下午
 **/
@RestController
@RequestMapping("/tinyUrlManager")
@Slf4j
@Api(tags = {"短地址管理"})
public class TinyUrlManagerController {

    @Autowired
    private TinyUrlCoreService tinyUrlCoreService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @PostMapping(value = "/v1/create", produces = "application/json")
    public Result<String> create(@RequestBody @Validated CreateTinyUrlRequest createTinyUrlRequest) {
        //url校验
        if (!UrlUtil.isValidationUrl(createTinyUrlRequest.getLongUrl())) {
            throw new TinyUrlException(TinyUrlExceptionCodeEnum.INVALID_URL);
        }
        // 校验
        String shortUrl = tinyUrlCoreService.generateShortUrl(createTinyUrlRequest.getLongUrl());
        return new Result<>(shortUrl);
    }


    @ApiOperation(value = "短域名读取接口", notes = "接受短域名信息，返回长域名信息")
    @PostMapping(value = "/v1/getOriginalUrl", produces = "application/json")
    public Result<String> getOriginalUrl(@RequestBody @Validated GetOriginalUrlRequest originalUrlRequest) {
        //url校验
        if (!UrlUtil.isValidationUrl(originalUrlRequest.getShortUrl())) {
            throw new TinyUrlException(TinyUrlExceptionCodeEnum.INVALID_URL);
        }
        return new Result<>(tinyUrlCoreService.getLongUrl(originalUrlRequest.getShortUrl()));
    }

}
