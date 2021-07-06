package com.assignment.domain.api.model;

public class OkResult extends RespResult {
    public OkResult(String payload) {
        super(true, "OK", payload);
    }
}
