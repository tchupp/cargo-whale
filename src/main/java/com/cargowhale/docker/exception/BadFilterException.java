package com.cargowhale.docker.exception;

public class BadFilterException extends Exception{

    public BadFilterException(String message){
        super(message);
    }

    public BadFilterException(String message, Throwable thrown){
        super(message, thrown);
    }
}
