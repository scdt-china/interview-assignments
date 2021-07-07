package com.sequoia.shorturl.common;

import lombok.Data;
import org.apache.log4j.spi.ErrorCode;
import java.io.Serializable;
/**
 *
 * 接口返回结果json对象
 *
 * @Author xj
 *
 * @Date 2021/06/27
 *
 * @version v1.0.0
 *
 */
@Data
public class ResponseResult<T> implements Serializable {

    private T data;
    private int code;
    private String message;

    /**
     * 处理成功接口
     * @return
     */
    public ResponseResult() {
        this.code = 0;
        this.message = "success";
    }

    /**
     * 处理失败接口
     * @param error
     * @return
     */
    public ResponseResult responseError(String error) {
        return new ResponseResult(1, error,null);
    }

    /**
     * 处理返回数据接口
     *
     * @param msg
     * @param data
     * @return
     */
    public ResponseResult(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

}
