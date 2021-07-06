package com.assignment.domain.api.model;

public class RespResult {
    private boolean success;
    private String msg;
    private String payload;

    public RespResult(boolean success, String msg, String payload) {
        this.success = success;
        this.msg = msg;
        this.payload = payload;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getPayload() {
        return payload;
    }
}
