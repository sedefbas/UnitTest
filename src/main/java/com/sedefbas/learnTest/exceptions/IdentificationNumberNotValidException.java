package com.sedefbas.learnTest.exceptions;


public class IdentificationNumberNotValidException extends RuntimeException{
    public IdentificationNumberNotValidException(String message){
        super(message);
    }
}
