package com.example.eshop.exceptions;

import org.springframework.http.HttpStatus;

public class ValidationFailedException extends HttpException{

    public ValidationFailedException(){
        super(HttpStatus.BAD_REQUEST, null);
    }

    public ValidationFailedException(Object data){
        super(HttpStatus.BAD_REQUEST, data);
    }
}
