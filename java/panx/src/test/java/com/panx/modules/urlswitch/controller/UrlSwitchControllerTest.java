package com.panx.modules.urlswitch.controller;

import com.panx.utils.ResultUtils;
import com.panx.modules.urlswitch.entity.UrlVo;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
@SuppressWarnings("deprecation")
class UrlSwitchControllerTest{
    @Autowired
    private UrlSwitchController urlSwitchController;
    @Test
    void urlLongToShortWithError() {
        System.out.print("测试错误的url格式\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setShortUrl("cuowuxinxi");
        ResultUtils resultUtils = urlSwitchController.getLongUrl(urlVo);
        UrlVo resultUrl = (UrlVo) resultUtils.getDatas();
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
        urlVo = new UrlVo();
        urlVo.setLongUrl("www.baidu.com/");
        resultUtils = urlSwitchController.getShortUrl(urlVo);
        resultUrl = (UrlVo) resultUtils.getDatas();
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlLongToShortWithOutMatch() {
        System.out.print("测试获取无匹配的长域名\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaiusdg");
        ResultUtils resultUtils = urlSwitchController.getShortUrl(urlVo);
        UrlVo resultUrl = (UrlVo) resultUtils.getDatas();
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlShortToLongWithOutMatch() {
        System.out.print("测试获取无匹配的短域名\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setShortUrl("http://www.baidu.com/G9INzGra");
        ResultUtils resultUtils = urlSwitchController.getLongUrl(urlVo);
        UrlVo resultUrl = (UrlVo) resultUtils.getDatas();
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nshortUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlLongToShortWithMatch() {
        System.out.print("测试先录入长域名再用该长域名获取\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaaaa");
        ResultUtils resultUtils = urlSwitchController.getShortUrl(urlVo);
        UrlVo resultUrl = (UrlVo) resultUtils.getDatas();
        urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighaaaa");
        resultUtils = urlSwitchController.getShortUrl(urlVo);
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }

    @Test
    void urlShortToLongWithMatch() {
        System.out.print("测试先录入短域名再用该短域名获取\n");
        UrlVo urlVo = new UrlVo();
        urlVo.setLongUrl("http://www.baidu.com/asfasuighbbbb");
        ResultUtils resultUtils = urlSwitchController.getShortUrl(urlVo);
        UrlVo resultUrl = (UrlVo) resultUtils.getDatas();
        urlVo = new UrlVo();
        urlVo.setShortUrl(resultUrl.getShortUrl());
        resultUtils = urlSwitchController.getLongUrl(urlVo);
        System.out.print("接口信息:"+ resultUtils.getResp_msg()+"\n");
        System.out.print("获取结果：shortUrl:"+resultUrl.getShortUrl()+"\nlongUrl:"+resultUrl.getLongUrl()+"\n");
    }
}