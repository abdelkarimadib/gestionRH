package com.demo.login.exception;

public class WrongPasswordException extends RuntimeException{

    public WrongPasswordException(String msg){
        super(msg);
    }
}
