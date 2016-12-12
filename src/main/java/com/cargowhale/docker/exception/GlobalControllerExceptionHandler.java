package com.cargowhale.docker.exception;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Bad Filter")
    @ExceptionHandler(value = BindException.class)
    public ResponseEntity<CargoWhaleErrorMessage> handleBadFilter(final HttpServletRequest request, final Exception ex) {
        return new ResponseEntity<>(new CargoWhaleErrorMessage(request.getRequestURI(), "Bad Filter", ex.getClass().toString()), HttpStatus.BAD_REQUEST);
    }
}
