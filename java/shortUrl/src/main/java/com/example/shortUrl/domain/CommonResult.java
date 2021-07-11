package com.example.shortUrl.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value="commonResult", description="返回值")
public class CommonResult<T> {
    private String code;
    private String message;
    private T data;

    public static <T> CommonResult success(T data) {
        CommonResult<T> result = new CommonResult<>();
        result.setCode("200");
        result.setMessage("success");
        result.setData(data);
        return result;
    }
}
