package com.cargowhale.docker.controller;

import com.cargowhale.docker.exception.BadFilterException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpServerErrorException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = HttpServerErrorException.class)
    public ResponseEntity<String> handleBadFilter(HttpServerErrorException ex) throws HttpServerErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<String> response = new ResponseEntity<>(ex.getResponseBodyAsString(),headers,HttpStatus.BAD_REQUEST);
        return response;
    }

}
