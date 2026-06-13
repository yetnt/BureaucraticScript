package com.yetnt.errs;

public class BureaucraticError extends Exception {
    public BureaucraticError(String message) {
        super(message);
    }

    public BureaucraticError(String message, Throwable cause) {
        super(message, cause);
    }

    public BureaucraticError(Throwable cause) {
        super(cause);
    }
}
