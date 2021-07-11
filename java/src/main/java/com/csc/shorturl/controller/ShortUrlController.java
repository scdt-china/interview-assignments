package com.csc.shorturl.controller;

import com.csc.shorturl.util.shortUrlUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class ShortUrlController {

    private static ConcurrentHashMap<String,String> dic=new ConcurrentHashMap<String,String>();


    /**
     * 生成短链接
     * @param url
     * @return Caron
     */
    @ApiOperation("根据长链接获取短链接")
    @RequestMapping(value = "/getsurl", method = RequestMethod.GET)
    @ResponseBody
    public Object save(String url){

        if (url == null || "".equals(url)){
            return "参数不能为空";
        }
        if(url.startsWith("http://") || url.startsWith("https://")){
            String[] shorturl= shortUrlUtil.shortUrl(url);
            Random ra =new Random();
            String result="http://test."+shorturl[ra.nextInt(4)];
            dic.put(result,url);
            return result;


        }else{
            return "网址必须以http或https开头";
        }
    }

    /**
     * 生成长链接
     * @param url
     * @return
     */
    @ApiOperation("根据短链接获取长链接")
    @RequestMapping(value = "/getlurl" , method = RequestMethod.GET)
    @ResponseBody
    public String restoreUrl(String url){

       // String restoreUrl = linkService.restoreUrl("http://cni.tips/"+url);

        if (url == null || "".equals(url)){
            return "参数不能为空";

        }else{
            if(dic.containsKey( url)) {
                String lurl = dic.get(url);
                return dic.get(url);
            }
            else
            {
                return "没有此地址对应长地址";
            }
//
        }

    }

}


