package com.scdtchina.sdns.rest;

import com.scdtchina.sdns.service.ShortUrlService;
import com.scdtchina.sdns.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@Api(tags = "短域名服务")
public class ShortUrlController {

    private final ShortUrlService shortUrlService;

    @ApiOperation("短域名存储接口：接受长域名信息，返回短域名信息")
    @PostMapping("/save")
    public JsonResult<SaveResponse> save(HttpServletRequest request, HttpServletResponse response, SaveRequest saveRequest) {
        String shortUrl = shortUrlService.save(saveRequest.getNormalUrl());
        SaveResponse saveResponse = new SaveResponse(shortUrl);
        return JsonResult.successResult(saveResponse);
    }

    @ApiOperation("短域名读取接口：接受短域名信息，返回长域名信息")
    @PostMapping("/find")
    public JsonResult<FindResponse> find(HttpServletRequest request, HttpServletResponse response, FindRequest findRequest) {
        String normalUrl = shortUrlService.find(findRequest.getShortUrl());
        if (StringUtils.isNotEmpty(normalUrl)) {
            FindResponse findResponse = new FindResponse(normalUrl);
            return JsonResult.successResult(findResponse);
        } else {
            return JsonResult.result("0404", "NOT FOUND", null);
        }
    }
}
