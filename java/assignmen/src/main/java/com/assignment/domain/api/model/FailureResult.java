package com.assignment.domain.api.model;

public class FailureResult extends RespResult {
    public FailureResult(String cause) {
        super(false, "failure, cause: " + cause, null);
    }
}
