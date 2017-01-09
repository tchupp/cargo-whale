package com.cargowhale.docker.client.core;

import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class DockerRestTemplate extends RestTemplate {

    public DockerRestTemplate(final DockerErrorHandler errorHandler, final ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        setErrorHandler(errorHandler);
    }
}
