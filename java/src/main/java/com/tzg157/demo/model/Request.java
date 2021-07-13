package com.tzg157.demo.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Request {

    @NotNull(message = "url不能为kong")
    @ApiModelProperty("待转换的域名url")
    private String url;
}
