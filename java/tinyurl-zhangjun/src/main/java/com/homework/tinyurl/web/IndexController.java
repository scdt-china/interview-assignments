package com.homework.tinyurl.web;

import com.homework.tinyurl.service.TinyUrlCoreService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @Deacription 跳转测试
 * @Author zhangjun
 * @Date 2021/7/18 7:15 下午
 **/
@RestController
@Slf4j
@RequestMapping("/s")
@Api(tags = {"短地址路由"})
public class IndexController {

    @Autowired
    private TinyUrlCoreService tinyUrlCoreService;

    @RequestMapping("/{shortPath}")
    public void router(@PathVariable("shortPath") String shortPath, HttpServletResponse response) {
        try {
            String originalUrl = tinyUrlCoreService.getLongUrl(shortPath);
            if (StringUtils.isEmpty(originalUrl)) {
                response.setStatus(HttpStatus.NOT_FOUND.value());
                return;
            }
            response.setHeader("Location", originalUrl);
            response.setStatus(HttpStatus.FOUND.value());
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
