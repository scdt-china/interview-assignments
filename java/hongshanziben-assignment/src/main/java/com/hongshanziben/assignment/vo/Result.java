package com.hongshanziben.assignment.vo;

import java.io.Serializable;

/**
 * Created by KINGSJ.YUAN@FOXMAIL.COM on 2021-07-08.
 */
public class Result<T> implements Serializable {

    private static final String SUCCESS = "200";

    private static final String ERROR = "500";

    private static final String MSG_SUCCESS = "操作成功";

    private static final String MSG_ERROR = "操作失败";

    private T data;

    private String msg;

    private String code;

    public Result(T data, String msg, String code) {
        this.data = data;
        this.msg = msg;
        this.code = code;
    }

    public Result(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public static <T> Result success(T data) {
        Result suc = new Result(data, MSG_SUCCESS, SUCCESS);
        return suc;
    }

    public static <T> Result error() {
        Result error = new Result(MSG_ERROR , ERROR);
        return error;
    }
}
