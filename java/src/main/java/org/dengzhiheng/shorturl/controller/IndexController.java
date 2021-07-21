package org.dengzhiheng.shorturl.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.dengzhiheng.shorturl.configuration.ServerInitConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.net.UnknownHostException;

/**
 * 默认入口
 * @Author: When6passBye
 * @Date: 2021-07-13 10:56
 **/
@Controller
@Api(value = "默认入口类")
public class IndexController {
    @Resource
    private ServerInitConfiguration serverInitConfiguration;

    @ApiOperation(value = "前端访问地址，在网页使用短网址服务")
    @GetMapping(value = "/")
    public ModelAndView index() throws UnknownHostException {
        ModelAndView mv = new ModelAndView("index");
        mv.addObject("domain", serverInitConfiguration.getUrl());
        return mv;
    }
}
