package com.vo;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("短域名对象")
public class ShortUrlVo implements Serializable {

    private static final long serialVersionUID = 8595399514967770543L;

    @ApiModelProperty("短域名")
    private String shortUrl;

    @ApiModelProperty(value = "长域名", example = "www")
    private String longUrl;

    @ApiModelProperty(value = "code", example = "0")
    private Integer code;

    @ApiModelProperty(value = "提示信息", example = "成功")
    private String msg;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
