package com.cargowhale.docker.config;

import com.cargowhale.docker.client.core.DockerErrorHandler;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.client.core.UnixComponentsClientHttpRequestFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestTemplateConfiguration {

    private final DockerProperties properties;
    private final DockerErrorHandler errorHandler;

    @Autowired
    public RestTemplateConfiguration(final DockerProperties properties, final DockerErrorHandler errorHandler) {
        this.properties = properties;
        this.errorHandler = errorHandler;
    }

    @Bean
    public DockerRestTemplate dockerRestTemplate() {
        UnixComponentsClientHttpRequestFactory requestFactory = new UnixComponentsClientHttpRequestFactory(this.properties.getUri());

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(new MappingJackson2HttpMessageConverter(configureObjectMapper()));
        messageConverters.add(new StringHttpMessageConverter());

        DockerRestTemplate restTemplate = new DockerRestTemplate(this.errorHandler, requestFactory);
        RootUriTemplateHandler.addTo(restTemplate, this.properties.getUri());
        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    private ObjectMapper configureObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new GuavaModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }
}
