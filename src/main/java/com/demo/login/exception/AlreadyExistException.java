package com.demo.login.exception;

public class AlreadyExistException extends RuntimeException{

    public AlreadyExistException(String msg){
        super(msg);
    }
}
