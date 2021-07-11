package com.example.shortUrl.controller;

import com.example.shortUrl.domain.CommonResult;
import com.example.shortUrl.domain.LongUrlReq;
import com.example.shortUrl.util.IdConverter;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/shorturl")
@Api(tags="短域名服务",description = "短域名服务")
/**
 * 使用自增id的方式来生成短域名。只服务一家公司级别的话足够。不会产生用完的情况。如果使用hash的方式则需要处理hash冲突的情况。
 */
public class ShortUrlController {
    Map<String, String> shortUrlDb = new ConcurrentHashMap<>();
    // 生产环境使用redis进行自增
    private static long autoIncrement;
    private static long MAX_LENGTH = 10;

    @GetMapping("/{url}")
    @ApiOperation(value = "取得长域名",notes = "取得长域名")
    private Mono<CommonResult<String>> getLongUrl(@PathVariable String url) {
        return Mono.just(CommonResult.success(shortUrlDb.get(url)));
    }
    @PostMapping("/add")
    @ApiOperation(value = "生成短域名",notes = "生成短域名")
    private Mono<CommonResult<String>> getShortUrl(@RequestBody LongUrlReq req) {
        if(shortUrlDb.keySet().size() > MAX_LENGTH) {
            CommonResult result = new CommonResult();
            result.setCode("501");
            result.setMessage("超过最大数");
            result.setData("");
            return Mono.just(result);
        }
        String key = IdConverter.ten2SixtyTwo(getUrlId());
        shortUrlDb.put(key, req.getLongUrl());
        return Mono.just(CommonResult.success(key));
    }

    private synchronized long getUrlId() {
        return autoIncrement++;
    }
}
