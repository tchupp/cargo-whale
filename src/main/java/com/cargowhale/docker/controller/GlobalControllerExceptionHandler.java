package com.cargowhale.docker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ExceptionHandler(value = HttpServerErrorException.class)
//    public ResponseEntity<String> handleBadFilter(HttpServerErrorException ex) throws HttpServerErrorException {
//        return new ResponseEntity<>(ex.getResponseBodyAsString(), HttpStatus.BAD_REQUEST);
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<String> handleBadFilter(BindException ex) throws HttpServerErrorException {
        String body = "{";
        body+="\"error\":\"Bad Request\",";
        body+="\"msg\":\"Bad Filter\"";
        body+="}";
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

}
