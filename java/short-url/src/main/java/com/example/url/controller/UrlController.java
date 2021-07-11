package com.example.url.controller;

import com.example.url.service.IShortUrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 短网址重定向
 */
@Controller
@RequestMapping("/s")
@Api(value = "短网址重定向接口", tags = {"用于提供短网址重定向接口"})
public class UrlController {

    @Resource
    private IShortUrlService shortUrlService;

    @GetMapping("/{id}")
    @ApiOperation(value = "短网址访问", notes = "短网址访问", httpMethod = "GET")
    public void redirect(@PathVariable String id, HttpServletResponse response) throws IOException {
        String longUrl = shortUrlService.decode(id);
        if (longUrl == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } else {
            response.setHeader("Location", longUrl);
            response.setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        }
    }
}
