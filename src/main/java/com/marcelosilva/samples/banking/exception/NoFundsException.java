package com.marcelosilva.samples.banking.exception;

public class NoFundsException extends Exception {

    public NoFundsException() {
    }

    public NoFundsException(String message) {
        super(message);
    }

    public NoFundsException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoFundsException(Throwable cause) {
        super(cause);
    }

    public NoFundsException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
