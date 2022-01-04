package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dto.VendingMachine;

public class InsufficientFundsException extends Exception {
    public InsufficientFundsException(String message){
        super(message);
    }

    public InsufficientFundsException(String message, Throwable cause){
        super(message, cause);
    }

    public InsufficientFundsException() {

    }
}
