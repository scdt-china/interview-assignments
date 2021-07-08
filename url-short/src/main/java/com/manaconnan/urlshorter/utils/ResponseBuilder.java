package com.manaconnan.urlshorter.utils;

import com.manaconnan.urlshorter.model.BaseResponse;
import com.manaconnan.urlshorter.model.ResponseCode;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public class ResponseBuilder {
    private static final String SUCCESS ="SUCCESS";
    public static BaseResponse<String> buildSuccessResponse(){
        return new BaseResponse<String>()
                .setCode(ResponseCode.SUCCESS)
                .setMessage(SUCCESS);
    }
   public static <T> BaseResponse<T> buildSuccessResponse(T data){
        return new BaseResponse<T>()
                .setCode(ResponseCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(data);
    }

    public static BaseResponse<String> buildFailedResponse(String msg){
        return new BaseResponse<String>()
                .setCode(ResponseCode.SUCCESS)
                .setMessage(SUCCESS)
                .setData(msg);
    }

}
