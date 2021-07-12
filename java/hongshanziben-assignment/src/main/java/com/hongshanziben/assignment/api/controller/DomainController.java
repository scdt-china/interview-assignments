package com.hongshanziben.assignment.api.controller;

import com.hongshanziben.assignment.api.service.DomainService;
import com.hongshanziben.assignment.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SJYUAN:KINGSJ.YUAN@FOXMAIL.COM
 * @date 2021-07-08.
 */
@RestController
@RequestMapping("/api/domain")
@Api(value = "域名转换", tags = "域名转换相关服务")
public class DomainController {

    @Autowired
    private DomainService domainService;

    @PostMapping("/createShortDomain")
    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息")
    public Result createShortDomain(@ApiParam(name = "url", value = "长域名信息", required = true)String url) {
        String shortDomain = domainService.createShortDomain(url);
        return Result.success(shortDomain);
    }

    @PostMapping("/getShortDomain")
    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息")
    public Result getShortDomain(@ApiParam(name = "url", value = "短域名信息", required = true)String url) {
        String shortDomain = domainService.getDomain(url);
        return Result.success(shortDomain);
    }

}
