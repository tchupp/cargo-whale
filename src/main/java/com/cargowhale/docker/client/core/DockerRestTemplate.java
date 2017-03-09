package com.cargowhale.docker.client.core;

import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.List;

public class DockerRestTemplate extends RestTemplate {

    public DockerRestTemplate(final DockerErrorHandler errorHandler, final ClientHttpRequestFactory requestFactory, final List<HttpMessageConverter<?>> messageConverters, final RootUriTemplateHandler uriTemplateHandler) {
        super(requestFactory);
        setErrorHandler(errorHandler);
        setMessageConverters(messageConverters);
        setUriTemplateHandler(uriTemplateHandler);
    }

    public <T> T getForObject(final String url, final ParameterizedTypeReference<T> responseType, final Object... uriVariables) {
        return exchange(url, HttpMethod.GET, HttpEntity.EMPTY, responseType, uriVariables).getBody();
    }
}