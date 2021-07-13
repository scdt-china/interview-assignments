package com.tzg157.demo.controller;

import com.tzg157.demo.model.Request;
import com.tzg157.demo.model.Response;
import com.tzg157.demo.model.UrlResult;
import com.tzg157.demo.service.DomainConvertService;
import com.tzg157.demo.service.DomainConvertServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

@Slf4j
@Api(tags = "域名转换服务")
@RequestMapping("/domain")
@RestController
public class DomainController {

    @Resource
    private DomainConvertService domainConvertService;

    @ApiOperation("长域名转为短域名")
    @PostMapping("/long2Short")
    public Response<UrlResult> longDomainToShortDomain(@Valid @RequestBody Request request, BindingResult bindingResult){
        Response<UrlResult> response = new Response<>();
        try {
            String shortUrl = request.getUrl();
            response = domainConvertService.convertToShort(shortUrl);
            log.info("longDomainToShortDomain invoke complete: request:{},response:{}",request,response);
        }catch (Exception e){
            response.setCode(Response.ResponseCode.FAIL.getCode());
            response.setMessage("Fail");
            log.error("shortDomainToLongDomain invoke error: request:{},errorMsg:{}",request,e.getMessage());
        }
        return response;
    }

    @ApiOperation("短域名转为长域名")
    @PostMapping("short2Long")
    public Response<UrlResult> shortDomainToLongDomain(@Valid @RequestBody Request request,BindingResult bindingResult){
        Response<UrlResult> response = new Response<>();
        try {
            String shortUrl = request.getUrl();
            response = domainConvertService.convertToLong(shortUrl);
            log.info("shortDomainToLongDomain invoke complete: request:{},response:{}",request,response);
        }catch (Exception e){
            response.setCode(Response.ResponseCode.FAIL.getCode());
            response.setMessage("Fail");
            log.error("shortDomainToLongDomain invoke error: request:{},errorMsg:{}",request,e.getMessage());
        }
        return response;
    }
}
