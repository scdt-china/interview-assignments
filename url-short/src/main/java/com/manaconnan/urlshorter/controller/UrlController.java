package com.manaconnan.urlshorter.controller;

import com.manaconnan.urlshorter.manager.UrlManager;
import com.manaconnan.urlshorter.model.BaseResponse;
import com.manaconnan.urlshorter.model.request.UrlRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
@RestController
@Api(tags = "url converter controler")
public class UrlController {
    @Autowired
    private UrlManager urlManager;


    @ApiOperation(value = "convertShort",response = BaseResponse.class)
    @PostMapping("/convertShort")
    public BaseResponse<String> convertShort(@RequestBody UrlRequest request){

        return urlManager.convertShortUrl(request);
    }
    @ApiOperation(value = "getOriginUrl",response = BaseResponse.class)
    @PostMapping("/getOriginUrl")
    public BaseResponse<String> getOriginUrl(@RequestBody UrlRequest request){
        return urlManager.getOriginUrl(request);
    }

}
