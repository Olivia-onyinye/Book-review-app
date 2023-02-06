package com.holyvia.Bookreview.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookNotFoundException extends RuntimeException{
    private String debugMessage;

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(String message, String debugMessage) {
        super(message);
        this.debugMessage = debugMessage;
    }
}
