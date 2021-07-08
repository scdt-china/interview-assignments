package com.example.shorturl.vos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author : shenhc
 * @date : 2021/7/7
 * desc:
 */
@ApiModel(value = "Longurl请求参数")
@Data
public class LongurlReqVo {

    @ApiModelProperty(value = "链接域名", example = "oIpU0")
    private String url;
}
