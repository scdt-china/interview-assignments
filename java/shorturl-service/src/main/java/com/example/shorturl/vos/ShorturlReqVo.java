package com.example.shorturl.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : shenhc
 * @date : 2021/7/7
 * desc:
 */
@ApiModel(value =  "Shorturl请求参数")
@Data
public class ShorturlReqVo {

    @ApiModelProperty(value = "链接域名",example="www.baidu.com")
    private String url;
}
