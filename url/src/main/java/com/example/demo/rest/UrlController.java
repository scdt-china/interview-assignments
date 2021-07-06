package com.example.demo.rest;

import com.example.demo.service.UrlService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UrlController {
    @Autowired
    private UrlService urlService;

    /**
     * 输入长 返回短
     * @param url
     * @return Caron
     */

    @RequestMapping ("/long")
    @ResponseBody
    @ApiOperation(value="输入长域名，返回短域名", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name="url",dataTypeClass = String.class,value = "长域名")})
    public Object save(String url){

        if (url == null || "".equals(url)){
            return null;
        }
        if(url.startsWith("http://") || url.startsWith("https://")){
            return  urlService.save(url);
        }else{
            return "网址必须以http或https开头";
        }
    }

    /**
     * 输入短 返回长
     * @param url
     * @return
     */
    @GetMapping ("/short")
    @ResponseBody
    @ApiOperation(value="输入短域名,返回长域名", httpMethod = "GET")
    @ApiImplicitParams(value = {@ApiImplicitParam(name="url",dataTypeClass = String.class,value = "短域名")})
    public String restoreUrl(String url){
        return urlService.restoreUrl(url);
    }

}
