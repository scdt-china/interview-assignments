package com.sequoiacapital.assignment.web.controller;

import com.sequoiacapital.assignment.common.resp.Result;
import com.sequoiacapital.assignment.service.UrlChangeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 链接转换
 *
 * @Author: xin.wu
 * @Date: Created in 2021/6/24 16:28
 * @Version: 1.0
 */
@Api(tags = "URL转换接口")
@RestController
@RequestMapping("assignment/v1")
public class UrlChangeController {

    @Autowired
    private UrlChangeService urlChangeService;

    /**
     * 长链接转变成短链接
     * @author xin.wu
     * @date 2021/6/24
     * @param url
     * @return com.sequoiacapital.assignment.common.resp.Result<java.lang.String>
     */
    @ApiOperation(value="change",notes = "长链接转变成短链接")
    @ApiImplicitParams({
            @ApiImplicitParam(name="url",value="长链接URL",required = true)
    })
    @GetMapping(value = "change")
    public Result<String> change(@RequestParam("url") String url) {
        String res = urlChangeService.getShortUrl(url);
        return Result.success(res);
    }

    /**
     * 短链接还原成长链接
     * @author xin.wu
     * @date 2021/6/24
     * @param key
     * @return com.sequoiacapital.assignment.common.resp.Result<java.lang.String>
     */
    @ApiOperation(value="restore",notes = "短链接还原成长链接")
    @GetMapping(value = "restore/{key}")
    public Result<String> restore(@PathVariable("key") String key) {
        String res = urlChangeService.getLongUrl(key);
        return Result.success(res);
    }

}
