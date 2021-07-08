package com.manaconnan.urlshorter.exception;

/**
 * @Author mazexiang
 * @CreateDate 2021/7/3
 * @Version 1.0
 */
public class ShortUrlException extends RuntimeException {
    public ShortUrlException() {
    }

    public ShortUrlException(String message) {
        super(message);
    }

    public ShortUrlException(String message, Throwable cause) {
        super(message, cause);
    }
}
