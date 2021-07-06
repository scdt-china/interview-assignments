package com.exception;

public class SystemException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    private int code;
    private String message;

    public SystemException(int code, String msg) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public int getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}