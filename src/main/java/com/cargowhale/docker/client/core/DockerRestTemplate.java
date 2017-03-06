package com.cargowhale.docker.client.core;

import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.Assert;
import org.springframework.web.client.*;

import java.io.IOException;
import java.net.URI;
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

    @Override
    protected <T> T doExecute(final URI url, final HttpMethod method, final RequestCallback requestCallback, final ResponseExtractor<T> responseExtractor) throws RestClientException {
        Assert.notNull(url, "'url' must not be null");
        Assert.notNull(method, "'method' must not be null");

        ClientHttpResponse response;
        try {
            ClientHttpRequest request = createRequest(url, method);
            if (requestCallback != null) {
                requestCallback.doWithRequest(request);
            }
            response = request.execute();
            handleResponse(url, method, response);
            if (responseExtractor != null) {
                return responseExtractor.extractData(response);
            } else {
                return null;
            }
        } catch (final IOException ex) {
            String resource = url.toString();
            String query = url.getRawQuery();
            resource = (query != null ? resource.substring(0, resource.indexOf(query) - 1) : resource);
            throw new ResourceAccessException("I/O error on " + method.name() + " request for \"" + resource + "\": " + ex.getMessage(), ex);
        }
    }
}