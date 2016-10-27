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
    @ExceptionHandler(value = BadFilterException.class)
    public ResponseEntity<String> handleBadFilter(BadFilterException ex) throws HttpServerErrorException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        ResponseEntity<String> response = new ResponseEntity<String>(errorBuilder(ex),headers,HttpStatus.BAD_REQUEST);
        return response;

    }

    private String errorBuilder(BadFilterException ex){
        String response = "{";
        response += "\"error\":\""+ex.getMessage()+"\"";
        response += "}";
        return response;
    }

}
