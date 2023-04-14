package com.example.eshop.exceptions;

import org.springframework.http.HttpStatus;

public class YouAreNotTheOwnerException extends HttpException{
    public YouAreNotTheOwnerException(){
        super(HttpStatus.BAD_REQUEST, null);
    }

    public YouAreNotTheOwnerException(Object data){
        super(HttpStatus.BAD_REQUEST, data);
    }
}
