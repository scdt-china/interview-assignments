package org.dengzhiheng.shorturl.controller;

import io.swagger.annotations.*;
import org.dengzhiheng.shorturl.Result.Result;
import org.dengzhiheng.shorturl.service.UrlConvertService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 短地址转换
 * @Author: When6passBye
 * @Date: 2021-07-12 16:06
 **/
@RestController
@Api(value = "服务提供类：" +
        "1.内存采用LRU存储，在规定内存满的时候（95%）淘汰掉最近最少使用的数据" +
        "2.内存多线程下线程安全")
public class MainController {
    @Resource
    private UrlConvertService urlConvertService;

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param url 长域名信息
     * @return 短域名信息
     */
    @ApiOperation(value = "短域名存储接口：接受长域名信息，返回短域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "url", value = "长域名", required = true, dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "域名不合法"),
            @ApiResponse(code = 500, message = "服务器内部错误，短地址长度超出8位") })
    @PostMapping("/convert")
    public Result<String> convertUrl(@RequestParam String url) {
        return urlConvertService.convertUrl(url);
    }

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param shortUrl : 短域名信息
     * @return : org.dengzhiheng.shorturl.Result.Result<java.lang.String>
     * @author : When6passBye
     * @date : 2021/7/19 上午9:23
     */
    @ApiOperation(value = "短域名读取接口：接受短域名信息，返回长域名信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shortUrl", value = "短域名", required = true, dataType = "String"),
    })
    @ApiResponses({ @ApiResponse(code = 200, message = "OK") ,
            @ApiResponse(code = 400, message = "没有找到对应的短域名，（内存被淘汰或者不存在）") })
    @PostMapping("/revert")
    public Result<String> revertUrl(@RequestParam String shortUrl) {
        return urlConvertService.revertUrl(shortUrl);
    }
}
