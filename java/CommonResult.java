package com.luo.assignment3.util;


public class CommonResult<T>
{
    private Integer code;
    private String  message;
    private T       data;



    public CommonResult(Integer code,String message,T data)
    {
       this.code=code;
       this.message=message;
       this.data = data;
    }

    public CommonResult(String message)
    {

        this(1,message,null);
    }
    public CommonResult(Integer code,String message)
    {

        this(code,message,null);
    }

    public CommonResult() {

    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
