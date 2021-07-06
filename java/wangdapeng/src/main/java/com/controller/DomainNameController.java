package com.controller;

import com.constant.CommonConstants;
import com.exception.SystemException;
import com.service.IDomainNameService;
import com.validator.DomainNameValidator;
import com.vo.ShortUrlVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/domain_name")
@Api(tags = {"短域名服务"}, description = "短域名长域名转换服务")
public class DomainNameController {

    @Autowired
    private DomainNameValidator domainNameValidator;

    @Autowired
    private IDomainNameService domainNameService;

    @GetMapping("/get_short_name")
    @ApiOperation("查询短域名")
    @ApiImplicitParam(name = "longUrl", value = "长域名", dataType = "String", paramType ="query", required = true, defaultValue = "www.baidu.com")
    public ShortUrlVo getShortUrl(@RequestParam String longUrl) {

        domainNameValidator.validateGetShortUrl(longUrl);
        ShortUrlVo vo = new ShortUrlVo();
        try {
            String shortUrl = domainNameService.getShortUrl(longUrl);
            vo.setLongUrl(longUrl);
            vo.setShortUrl(shortUrl);
            vo.setCode(CommonConstants.CODE_SUCCESS);
            vo.setMsg("success");
            return vo;
        } catch (SystemException ex) {
            vo.setLongUrl(longUrl);
            vo.setMsg(ex.getMessage());
            vo.setCode(ex.getCode());
            return vo;
        }

    }

    @GetMapping("/get_long_name")
    @ApiOperation("查询长域名")
    @ApiImplicitParam(name = "shortUrl", value = "短域名", dataType = "String", paramType ="query", required = true, defaultValue = "baidu")
    public ShortUrlVo getLongUrl(@RequestParam String shortUrl) {

        domainNameValidator.validateGetLongUrl(shortUrl);
        ShortUrlVo vo = new ShortUrlVo();
        try {
            String longUrl = domainNameService.getLongUrl(shortUrl);
            Integer code = CommonConstants.CODE_SUCCESS;
            vo.setLongUrl(longUrl);
            vo.setShortUrl(shortUrl);
            vo.setCode(code);
            vo.setMsg("success");
            return vo;
        } catch (SystemException ex) {
            vo.setShortUrl(shortUrl);
            vo.setMsg(ex.getMessage());
            vo.setCode(ex.getCode());
            return vo;
        }
    }

}
