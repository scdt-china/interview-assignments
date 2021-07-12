package com.luo.assignment3.controller;

import com.luo.assignment3.util.CommonResult;
import com.luo.assignment3.util.CommonStatic;
import com.luo.assignment3.util.CompressNumber;
import com.luo.assignment3.util.Tools;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @PackageName:com.luo.assignment3.controller
 * @ClassName: ApiController
 * @Description: 短域名长域名转换接口
 * @author: 罗天文
 * @date: 2021-06-25
 */

@Api(value = "短域名接口", tags = "短域名关的接口")
@RestController
@RequestMapping("/api")
public class ApiController {

    private static AtomicInteger atomicInteger = new AtomicInteger(0);



    @GetMapping("/getLongUrlByShortUrl")
    @ApiOperation("通过短链接获取长链接接口")

    private CommonResult getLongUrlByShortUrl(@ApiParam(name = "shortUrl", value = "短链接路径", required = true) @RequestParam("shortUrl")  String shortUrl) {
        String _value = (String) Tools.get(CommonStatic.SHORT_URL_KEY + shortUrl);
        if (!StringUtils.isEmpty(_value)) {
            return new CommonResult(1, "", _value);
        }
        return new CommonResult(0, "没有查询到长链接");
    }

    @GetMapping("/getShortUrlByLongUrl")
    @ApiOperation("通过长链接获取短链接接口")

    private CommonResult getShortUrlByLongUrl(@ApiParam(name = "longUrl", value = "长链接路径", required = true) @RequestParam("longUrl")  String longUrl) {
        String shortUrl = (String) Tools.get(CommonStatic.LONG_URL_KEY + longUrl);
        if (shortUrl == null) {
            synchronized (this) {
                String longString = CompressNumber.base62Encode(atomicInteger.incrementAndGet());
                shortUrl = CommonStatic.SHORT_URL + longString;
                Tools.set(CommonStatic.LONG_URL_KEY + longUrl, shortUrl);
                Tools.set(CommonStatic.SHORT_URL_KEY + shortUrl, longUrl);
            }
        }


        if (!StringUtils.isEmpty(shortUrl)) {
            return new CommonResult(1, "", shortUrl);
        }
        return new CommonResult(0, "没有查询到长链接");
    }


}
