package com.xiong.urlservice.boot.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: create by xiong
 * @version: v1.0
 * @description:
 * @date:2021/6/24 4:02 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("获取短连接-返回实体")
public class ShortUrlResponse {
    @ApiModelProperty(value = "原链接")
    private String originUrl;
    @ApiModelProperty(value = "短链接")
    private String shortUrl;

}
