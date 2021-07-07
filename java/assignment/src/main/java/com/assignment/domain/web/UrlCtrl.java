package com.assignment.domain.web;

import com.assignment.domain.kv.LRUMap;
import com.assignment.domain.response.ResponseResult;
import com.assignment.domain.util.NumberTransUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author: zhangruiqi03
 * @Date: 2021/6/28 11:02 PM
 */
@Api(tags = "短域名服务")

@RestController
@RequestMapping("/")
public class UrlCtrl {
    //  key前缀
    private static final String preFix = "RICKY-";
    // 模拟id自增
    //  private LongAdder idGenerator = new LongAdder();
    private AtomicLong idGenerator = new AtomicLong(1000);

    private LRUMap<String, String> lruMap = new LRUMap<>(100000);
    // 一定程度上避免长短映射浪费
    private ConcurrentHashMap<String, String> longShortMap = new ConcurrentHashMap(10000);

    //转短域名
    @ApiOperation(value = "长域名转短域名")
    @GetMapping(value = "short/{url}")
    public ResponseResult shortUrl(@PathVariable String url) throws Exception {
        String shortUrl = longShortMap.containsKey(url) ? longShortMap.get(url) : NumberTransUtil.toBase62(idGenerator.incrementAndGet());
        longShortMap.put(url, shortUrl);
        lruMap.put(preFix + shortUrl, url);
        return new ResponseResult("http://localhost:8080/" + shortUrl);

    }

    //转长域名
    @ApiOperation(value = "短域名获取长域名")
    @GetMapping(value = "origin/{shortUrl}")
    public ResponseResult originUrl(@PathVariable String shortUrl,
                                    HttpServletResponse response) throws Exception {
        ResponseResult result = new ResponseResult();
        if (!lruMap.containsKey(preFix + shortUrl)) {
            result.setCode(ResponseResult.error);
            result.setMsg("shortUrl非法");
        } else {
            result.setMsg(lruMap.get(preFix + shortUrl));
        }
        return result;
    }

}
