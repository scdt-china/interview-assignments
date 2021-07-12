package com.he.shorturl.controller;

import com.he.shorturl.service.ShortService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@Api(description = "短链")
@RestController
@CrossOrigin
@RequestMapping("/short")
public class UrlController {

    @Autowired
    ShortService shortService;



    @ApiOperation(value = "创建短链")
    @GetMapping("/create/{url}")
    public String create(@ApiParam(name = "url", value = "url 地址", required = true)
                             @PathVariable String url){
        String shorUrl = shortService.create(url);
        return shorUrl;
    }

    @ApiOperation(value = "原始链接")
    @GetMapping("/show/{shortCode}")
    public String show(@ApiParam(name = "shortCode", value = "短链code", required = true)
                         @PathVariable String shortCode){
        String url = shortService.show(shortCode);
        return url;
    }


}
