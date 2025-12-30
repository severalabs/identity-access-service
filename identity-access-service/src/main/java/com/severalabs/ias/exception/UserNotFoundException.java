package com.severalabs.ias.exception;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(String message){

        super(message);
    }
}
