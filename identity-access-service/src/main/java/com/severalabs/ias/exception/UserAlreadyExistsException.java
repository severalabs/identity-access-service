package com.severalabs.ias.exception;

public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message){
        super(message);
    }
}
