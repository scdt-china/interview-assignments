package com.sequoia.china.controller;

import com.sequoia.china.common.ResponseData;
import com.sequoia.china.service.IDomainNameConvertService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description 长短域名转换controller
 * @Author helichao
 * @Date 2021/6/24 6:48 下午
 */
@RestController
@RequestMapping("/domainNameconvert")
@Api(value = "长短域名转换接口")
public class DomainNameConvertController {

    @Autowired
    private IDomainNameConvertService domainNameConvertService;

    /**
     * 长域名转短域名
     * @param longDomainName 长域名
     * @return 短域名
     */
    @RequestMapping("/longToShort")
    @ApiImplicitParam(name = "longDomainName",value = "长域名")
    @ApiOperation(value = "长域名转短域名")
    ResponseData<String> longToShort(String longDomainName){
        return ResponseData.success(domainNameConvertService.longToShort(longDomainName));
    }

    /**
     * 短域名转长域名
     * @param shortDomainName 短域名
     * @return 长域名
     */
    @RequestMapping("/shortToLong")
    @ApiImplicitParam(name = "shortDomainName",value = "短域名")
    @ApiOperation(value = "短域名转长域名")
    ResponseData<String> shortToLong(String shortDomainName){
        return ResponseData.success(domainNameConvertService.shortToLong(shortDomainName));
    }

}
