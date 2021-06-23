package com.example.shortUrl.controller;

import com.example.shortUrl.domain.vo.JsonResult;
import com.example.shortUrl.service.ShortUrlService;
import com.example.shortUrl.utils.ShortUrlGenerator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @desc
 * @author shiqingshan
 * @Param
 * @Date 2021/5/20 上午10:31
 * @return
**/
@RestController
@Slf4j
@Api(tags = "短链接")
@RequestMapping("/shortUrl")
public class ShortUrlController {

    @Autowired
    ShortUrlService shortUrlService;

    @ApiOperation(value = "短链接生成", notes = "短链接生成")
    @PostMapping(value = "/generate")
    public JsonResult<String> generate(@RequestBody @ApiParam("正常链接") String url){
        if(!ShortUrlGenerator.isUrl(url)){
            return JsonResult.fail("非法url");
        }
        return JsonResult.ok(shortUrlService.generate(url));
    }


    @ApiOperation(value = "短链接还原", notes = "根据短链接还原原始链接")
    @GetMapping(value = "/resolve")
    public JsonResult<String> resolve(@ApiParam("短链接")@RequestParam("shortUrl") String shortUrl){


        return JsonResult.ok(shortUrlService.resolve(shortUrl));
    }
}
