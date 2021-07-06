package com.example.assignment.facade;


import com.example.assignment.service.UrlService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Api(value = "短链接服务Controller", tags = { "短链接访问接口" })
@RestController
public class UrlAPIFacde {

    @Autowired
    private UrlService urlService;

    @ApiOperation(value = "获取短链接")
    @RequestMapping(value="/getShortUrl",method= RequestMethod.POST)
    @ResponseBody
    public String getShortUrl(@ApiParam("长链接")  @RequestBody String longUrl){
        return urlService.convertToShort(longUrl);
    }

    @ApiOperation(value = "获取长链接")
    @RequestMapping(value="/getLongUrl",method= RequestMethod.POST)
    @ResponseBody
    public String getLongUrl(@ApiParam("短链接") @RequestBody String shortUrl){
        return urlService.convertToLong(shortUrl);
    }
}
