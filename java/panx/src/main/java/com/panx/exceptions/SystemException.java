package com.panx.exceptions;

public class SystemException extends Exception{
    private String message;

    public SystemException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
