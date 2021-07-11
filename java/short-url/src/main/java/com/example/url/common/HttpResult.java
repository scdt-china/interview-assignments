package com.example.url.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * @author ancx
 */
@Getter
@Setter
@ApiModel(value = "返回结果包装对象", description = "成功和失败的处理结果都装转进此类")
public class HttpResult<T> {

    @ApiModelProperty(value = "处理结果码", name = "code", example = "200")
    private int code;

    @ApiModelProperty(value = "处理信息/错误信息", name = "message", example = "成功")
    private String message;

    @ApiModelProperty(value = "处理结果码", name = "data")
    private T data;
}
