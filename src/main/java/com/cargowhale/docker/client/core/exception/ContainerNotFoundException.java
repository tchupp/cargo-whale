package com.cargowhale.docker.client.core.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Container not found")
public class ContainerNotFoundException extends IOException {
}
