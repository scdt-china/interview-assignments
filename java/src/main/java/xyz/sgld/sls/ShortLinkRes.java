package xyz.sgld.sls;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("短链接返回对象")
public class ShortLinkRes<T> {

    public static final int RES_CODE_OK = 1;
    public static final int RES_CODE_UNKNOWN_ERROR = -1;
    public static final int RES_CODE_ARGUMENT_ERROR = -2;
    public static final int RES_CODE_TIMEOUT_ERROR = -3;
    public static final int RES_CODE_MISSION_PARAMS = -4;
    public static final int RES_CODE_NOT_FOUND_ERROR = -5;

    public static final String RES_DES_OK = "OK";
    public static final String RES_DES_UNKNOWN_ERROR = "未知错误";
    public static final String RES_DES_ARGUMENT_ERROR = "参数错误";
    public static final String RES_DES_TIMEOUT_ERROR = "处理超时";
    public static final String RES_DES_MISSION_PARAMS = "缺少参数";
    public static final String RES_DES_NOT_FOUND_ERROR = "未找到资源";

    @ApiModelProperty("返回代码，代码值为1时表示成功")
    private int resCode;
    @ApiModelProperty("返回描述")
    private String des;
    @ApiModelProperty("返回数据")
    private T data;
    @ApiModelProperty("返回错误信息,默认为空")
    private Error error;

    public ShortLinkRes() {
    }

    public T getData() {
        return data;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getResCode() {
        return resCode;
    }

    public void setResCode(int resCode) {
        this.resCode = resCode;
    }

    @ApiModel("api 错误")
    public static class Error {
        @ApiModelProperty("错误代码")
        int errorCode;
        @ApiModelProperty("错误描述")
        String errorDesc;
    }

    public static final <T> ShortLinkRes<T> createOkRes() {
        return createRes(RES_CODE_OK, RES_DES_OK);
    }

    public static final <T> ShortLinkRes<T> createUnknownRes() {
        return createRes(RES_CODE_UNKNOWN_ERROR, RES_DES_UNKNOWN_ERROR);
    }

    public static final <T> ShortLinkRes<T> createMissParamsRes() {
        return createRes(RES_CODE_MISSION_PARAMS, RES_DES_MISSION_PARAMS);
    }

    public static final <T> ShortLinkRes<T> createTimeoutRes() {
        return createRes(RES_CODE_TIMEOUT_ERROR, RES_DES_TIMEOUT_ERROR);
    }

    public static final <T> ShortLinkRes<T> createArgsErrorRes() {
        return createRes(RES_CODE_ARGUMENT_ERROR, RES_DES_ARGUMENT_ERROR);
    }

    public static final <T> ShortLinkRes<T> createNotFoundRes() {
        return createRes(RES_CODE_NOT_FOUND_ERROR, RES_DES_NOT_FOUND_ERROR);
    }

    private static final <T> ShortLinkRes<T> createRes(int code, String des) {
        ShortLinkRes<T> res = new ShortLinkRes<T>();
        res.resCode = code;
        res.des = des;
        return res;
    }

    @Override
    public String toString() {
        return "ShortLinkRes:{resCode=" + resCode + ",des:" + des + ",data:" + data + ",error:" + error + "}";
    }
}
