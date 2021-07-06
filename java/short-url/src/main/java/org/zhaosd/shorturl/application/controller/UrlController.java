package org.zhaosd.shorturl.application.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zhaosd.shorturl.application.service.UrlApplicationService;
import org.zhaosd.shorturl.domain.Url;
import org.zhaosd.shorturl.domain.UrlDomainService;

/**
 * 短域名Rest接口服务类
 * @author mvt-zhaosandong-mac
 */
@Api("超链接转短链接服务接口")
@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    UrlDomainService urlDomainService;

    @Autowired
    UrlApplicationService urlApplicationService;

    @ApiOperation("长连接转短链接")
    @PostMapping("to-short")
    public String toShort(String srcUrl) {
        Url url = urlDomainService.toShortUrl(srcUrl);
        return url.getShortUrl();
    }

    @ApiOperation("短链接回查长连接")
    @GetMapping("to-long")
    public String toLong(String shortUrl) {
        Url url = urlApplicationService.getByShortUrl(shortUrl);
        return url.getSrcUrl();
    }

}
