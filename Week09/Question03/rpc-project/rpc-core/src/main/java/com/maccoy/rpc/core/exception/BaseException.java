package com.maccoy.rpc.core.exception;

public class BaseException extends RuntimeException {

    private String message;

    public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }
}
