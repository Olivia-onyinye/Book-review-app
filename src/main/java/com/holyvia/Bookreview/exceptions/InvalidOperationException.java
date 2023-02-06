package com.holyvia.Bookreview.exceptions;

public class InvalidOperationException extends RuntimeException {
    private String debugMessage;
    public InvalidOperationException(String message) {
        super(message);
        this.debugMessage=message;
    }
}
