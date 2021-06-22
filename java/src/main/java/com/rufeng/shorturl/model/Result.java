package com.rufeng.shorturl.model;

import com.rufeng.shorturl.enums.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qujingguo
 * @version 1.0.0
 * @date 2021/5/27 11:26 上午
 * @description 返回结果
 */
@Data
@ApiModel("返回结果")
public class Result<T> {

    @ApiModelProperty("code码")
    private String code;

    @ApiModelProperty("是否成功标识")
    private boolean success;

    @ApiModelProperty("错误信息")
    private String msg;

    @ApiModelProperty("返回实体")
    private T data;


    public static <T> Result<T> error(ErrorCode code) {
        Result<T> result = new Result<>();
        result.setCode(code.getCode());
        result.setMsg(code.getDesc());
        result.setSuccess(false);
        return result;
    }

    public static <T> Result<T> error(String code, String msg) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(false);
        return result;
    }


    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.setCode(ErrorCode.SUCCESS.getCode());
        result.setMsg(ErrorCode.SUCCESS.getDesc());
        result.setSuccess(true);
        result.setData(data);
        return result;
    }
}
