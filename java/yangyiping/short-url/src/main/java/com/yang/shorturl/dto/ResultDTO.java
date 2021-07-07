package com.yang.shorturl.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author yangyiping1
 */
@Data
@ApiModel("统一接口结果传输对象")
public class ResultDTO<T> {
    /**
     * 接口响应编码
     */
    @ApiModelProperty("响应码,参考http")
    private Integer code;
    /**
     * 接口返回信息
     */
    @ApiModelProperty("响应信息,有错误时显示错误信息")
    private String message;
    /**
     * 接口返回数据
     */
    @ApiModelProperty("响应码为200时是接口具体结果")
    private T data;
}
