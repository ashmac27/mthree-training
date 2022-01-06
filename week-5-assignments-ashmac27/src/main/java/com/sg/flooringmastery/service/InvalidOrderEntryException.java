package com.sg.flooringmastery.service;

public class  InvalidOrderEntryException extends Exception {
    public InvalidOrderEntryException(String message) {
        super(message);
    }

    public InvalidOrderEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
