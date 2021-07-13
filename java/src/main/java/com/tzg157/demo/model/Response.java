package com.tzg157.demo.model;


import lombok.Data;
import lombok.Getter;

@Data
public class Response<T> {

    private int code;
    private String message;
    private T result;

    @Getter
    public enum ResponseCode{
        SUCCESSOR(0),FAIL(1);
        private int code;
        ResponseCode(int code){
            this.code = code;
        }
    }
}
