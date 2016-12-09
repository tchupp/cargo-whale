package com.cargowhale.docker.client.core;

import org.springframework.web.client.RestTemplate;

public class DockerRestTemplate extends RestTemplate {

    public DockerRestTemplate(final DockerErrorHandler errorHandler) {
        setErrorHandler(errorHandler);
    }
}
