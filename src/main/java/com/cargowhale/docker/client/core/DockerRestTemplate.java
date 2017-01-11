package com.cargowhale.docker.client.core;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class DockerRestTemplate extends RestTemplate {

    public DockerRestTemplate(final DockerErrorHandler errorHandler, final ClientHttpRequestFactory requestFactory) {
        super(requestFactory);
        setErrorHandler(errorHandler);
    }

    public <T> T getForObject(final String url, final ParameterizedTypeReference<T> responseType) {
        return exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType).getBody();
    }
}
