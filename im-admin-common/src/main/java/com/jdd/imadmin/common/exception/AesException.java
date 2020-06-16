package com.jdd.imadmin.common.exception;

public class AesException extends Exception{
    public AesException(){
        super();
    }
    public AesException(String msg, Exception e){
        super(msg,e);
    }
    public AesException(String msg){
        super(msg);
    }
}