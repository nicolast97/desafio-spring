package com.spring.desafio.exceptions;

import lombok.Getter;

@Getter
public class QuantityException extends Exception{
    public QuantityException(String errorMessage){
        super(errorMessage);
    }
}
