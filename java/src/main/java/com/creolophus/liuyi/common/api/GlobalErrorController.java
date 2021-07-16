package com.creolophus.liuyi.common.api;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author magicnana
 * @date 2018/5/25.
 */
@ApiIgnore
@Controller
@RequestMapping("${server.error.path:${error.path:/error}}")
public class GlobalErrorController implements ErrorController {

    private static Logger logger = LoggerFactory.getLogger(GlobalErrorController.class);

    @Resource
    private ErrorInfoBuilder errorInfoBuilder;

    @RequestMapping(produces = {MediaType.APPLICATION_JSON_UTF8_VALUE})
    @ResponseBody
    public ResponseEntity error(HttpServletRequest request, HttpServletResponse response) {

        // 此处配置的是允许任意域名跨域请求，可根据需求指定
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods",
            "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "86400");
        response.setHeader("Access-Control-Allow-Headers", "*");

        ResponseEntity responseEntity = errorInfoBuilder.getErrorInfo(request, response);
        return responseEntity;
    }


    @Override
    public String getErrorPath() {
        return this.errorInfoBuilder.getErrorPath();
    }


}
