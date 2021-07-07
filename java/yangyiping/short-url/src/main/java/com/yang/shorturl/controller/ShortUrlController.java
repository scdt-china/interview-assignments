package com.yang.shorturl.controller;

import com.yang.shorturl.dto.ResultDTO;
import com.yang.shorturl.service.ShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @author yangyiping1
 */
@Slf4j
@RestController
@RequestMapping("/api/url")
@Api(tags = {"短地址服务接口"}, description = "短地址转换与反查服务")
public class ShortUrlController {

    private ResultDTO<String> errorDto;

    private ShortUrlService shortUrlService;

    @Autowired
    public void setShortUrlService(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @PostConstruct
    private void init() {
        errorDto = new ResultDTO<String>();
        errorDto.setCode(500);
        errorDto.setMessage("服务异常");
    }


    @GetMapping("/getShortUrl")
    @ApiOperation("获取短地址")
    @ApiImplicitParam(name = "url", value = "长地址", dataType = "String", paramType = "query", required = true, example = "http://www.baidu.com")
    ResultDTO<String> getShortUrl(@RequestParam String url) {
        String shortUrl = shortUrlService.getShortUrl(url);
        return makeResult(shortUrl);
    }

    @GetMapping("/getLongUrl")
    @ApiOperation("反查长地址")
    @ApiImplicitParam(name = "shortUrl", value = "短地址", dataType = "String", paramType = "query", required = true, example = "http://a.cn/aaaaaaaa")
    ResultDTO<String> getUrlFromShortUrl(@RequestParam String shortUrl) {
        String url = shortUrlService.getUrlFromShortUrl(shortUrl);
        return makeResult(url);
    }

    private ResultDTO<String> makeResult(String url) {
        if (StringUtils.hasLength(url)) {
            ResultDTO<String> resultDTO = new ResultDTO<String>();
            resultDTO.setCode(200);
            resultDTO.setMessage("ok");
            resultDTO.setData(url);
            return resultDTO;
        }
        return errorDto;
    }
}
