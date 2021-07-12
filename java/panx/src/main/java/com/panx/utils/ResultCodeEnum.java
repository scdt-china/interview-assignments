package com.panx.utils;

public enum ResultCodeEnum {
    SUCCESS(0),
    FAILED(1);

    private Integer code;

    private ResultCodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }
}
