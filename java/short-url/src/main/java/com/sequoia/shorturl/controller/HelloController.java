package com.sequoia.shorturl.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * restFull 服务启动测试接口  /sayHello
 *
 * @author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@RestController
@Api(value  = "测试接口",tags = "测试操作接口")
public class HelloController {


    @GetMapping("/sayHello")
    public String sayHello() {

        return "hi,hello !" ;
    }
}
