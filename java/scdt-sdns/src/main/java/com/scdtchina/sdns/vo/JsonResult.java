package com.scdtchina.sdns.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("REST请求结果包装")
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult<T> {

    public static final String SUCCESS_CODE = "0000";
    protected static final String SUCCESS_MSG = "ok";

    @ApiModelProperty("响应码: 0000代表正常")
    private String rc;
    @ApiModelProperty("响应信息: 默认为ok")
    private String msg;
    @ApiModelProperty("响应消息体: 被包装的业务VO对象")
    private T body;

    public JsonResult(String rc) {
        this.rc = rc;
    }

    public JsonResult(String rc, String msg) {
        this.rc = rc;
        this.msg = msg;
    }

    public static JsonResult successResult() {
        return new JsonResult(SUCCESS_CODE);
    }

    public static JsonResult successResult(Object object) {
        return new JsonResult(SUCCESS_CODE, SUCCESS_MSG, object);
    }

    public static JsonResult result(String rc, String msg, String object) {
        return new JsonResult(rc, msg, object);
    }

}
