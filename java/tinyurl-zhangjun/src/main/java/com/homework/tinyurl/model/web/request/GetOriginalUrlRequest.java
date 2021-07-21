package com.homework.tinyurl.model.web.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @Deacription
 * @Author zhangjun
 * @Date 2021/7/17 9:44 下午
 **/
@Data
@ApiModel("短域名读取接口请求体")
public class GetOriginalUrlRequest {
    @ApiModelProperty(value = "短域名", required = true)
    @NotBlank(message = "短域名地址不能为空")
    private String shortUrl;
}