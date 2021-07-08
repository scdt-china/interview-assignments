package com.example.shorturl.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : shenhc
 * @date : 2021/7/7
 * desc:
 */
@ApiModel(value =  "Longurl响应参数")
@Data
public class LongurlResVo {
    @ApiModelProperty(value = "链接域名",example="www.baidu.com")
    private String url;
}
