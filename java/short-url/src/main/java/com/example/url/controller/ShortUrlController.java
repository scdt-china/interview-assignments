package com.example.url.controller;

import com.example.url.common.HttpResult;
import com.example.url.enums.HttpResultEnum;
import com.example.url.service.IShortUrlService;
import com.example.url.utils.HttpResultUtil;
import com.example.url.utils.Util;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.ref.SoftReference;

@RestController
@RequestMapping("/shortUrl")
@Api(value = "短网址服务接口", tags = {"用于提供短网址服务接口"})
public class ShortUrlController {

    @Resource
    private IShortUrlService shortUrlService;
    /**
     * 设置1M的缓冲余地
     */
    public final SoftReference<Byte[]> memoryAvailable = new SoftReference<>(new Byte[1048576]);

    /**
     * 将长网址编码为短网址
     *
     * @param longUrl 长网址
     * @return 短网址
     */
    @PostMapping("/encode")
    @ApiOperation(value = "将长网址编码为短网址", notes = "将长网址编码为短网址", httpMethod = "POST")
    public HttpResult<String> encode(@RequestBody String longUrl) {
        if (memoryAvailable.get() == null) {
            // 内存不够了，停止写入了
            return HttpResultUtil.error(HttpResultEnum.MEM_NERVOUS);
        }
        if (Util.isNotUrl(longUrl)) {
            return HttpResultUtil.error(HttpResultEnum.LONG_URL_ERROR);
        }
        String shortUrl = shortUrlService.encode(longUrl);
        return HttpResultUtil.success(shortUrl);
    }

    /**
     * 将短网址解码为长网址
     *
     * @param shortUrl 短网址
     * @return 长网址
     */
    @PostMapping("/decode")
    @ApiOperation(value = "将短网址解码为长网址", notes = "将短网址解码为长网址", httpMethod = "POST")
    public HttpResult<String> decode(@RequestBody String shortUrl) {
        String longUrl = shortUrlService.decode(shortUrl);
        if (longUrl == null) {
            return HttpResultUtil.error(HttpResultEnum.SHORT_URL_NOT_EXISTS);
        } else {
            return HttpResultUtil.success(longUrl);
        }
    }
}
