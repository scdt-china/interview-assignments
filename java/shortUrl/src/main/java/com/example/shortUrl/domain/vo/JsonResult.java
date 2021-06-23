package com.example.shortUrl.domain.vo;

import com.example.shortUrl.constant.Constants;
import java.io.Serializable;
import lombok.Data;

/**
 * @author sting
 * @date 2021/5/20
 */
@Data
public class JsonResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 成功 */
    public static final int SUCCESS = Constants.SUCCESS;

    public static final String SUCCESS_MSG = Constants.SUCCESS_MSG;

    /** 失败 */
    public static final int FAIL = Constants.FAIL;

    public static final String FAIL_MSG = Constants.FAIL_MSG;

    private int code;

    private String msg;

    private T data;

    public static <T> JsonResult<T> ok()
    {
        return restResult(null, SUCCESS, SUCCESS_MSG);
    }

    public static <T> JsonResult<T> ok(T data)
    {
        return restResult(data, SUCCESS, SUCCESS_MSG);
    }

    public static <T> JsonResult<T> ok(T data, String msg)
    {
        return restResult(data, SUCCESS, msg);
    }

    public static <T> JsonResult<T> fail()
    {
        return restResult(null, FAIL, FAIL_MSG);
    }

    public static <T> JsonResult<T> fail(String msg)
    {
        return restResult(null, FAIL, msg);
    }

    public static <T> JsonResult<T> fail(T data)
    {
        return restResult(data, FAIL, FAIL_MSG);
    }

    public static <T> JsonResult<T> fail(T data, String msg)
    {
        return restResult(data, FAIL, msg);
    }

    public static <T> JsonResult<T> fail(int code, String msg)
    {
        return restResult(null, code, msg);
    }

    private static <T> JsonResult<T> restResult(T data, int code, String msg)
    {
        JsonResult<T> apiResult = new JsonResult<>();
        apiResult.setCode(code);
        apiResult.setData(data);
        apiResult.setMsg(msg);
        return apiResult;
    }
}
