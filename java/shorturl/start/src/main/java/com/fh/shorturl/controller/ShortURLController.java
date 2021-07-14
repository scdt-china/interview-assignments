package com.fh.shorturl.controller;

import com.fh.shorturl.common.ResponseVO;
import com.fh.shorturl.service.ShortURLService;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Api(tags = "短域名服务V1")
@ApiSupport(author = "ccfenghe@163.com")
@RestController
@RequestMapping("/url")
public class ShortURLController {
    Logger logger = LoggerFactory.getLogger(com.fh.shorturl.controller.ShortURLController.class);

    @Autowired
    ShortURLService shortURLService;

    @ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息")
    @GetMapping("/{url}")
    public ResponseVO addShortURL(@PathVariable(name = "url") String url) {
        return ResponseVO.data(shortURLService.addShortURL(url));
    }
}
