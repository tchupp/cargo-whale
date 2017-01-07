package com.cargowhale.docker.config;

import com.cargowhale.docker.client.core.DockerErrorHandler;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.client.core.UnixComponentsClientHttpRequestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

        DockerRestTemplate restTemplate = new DockerRestTemplate(this.errorHandler, requestFactory);
        RootUriTemplateHandler.addTo(restTemplate, this.properties.getUri());

        return restTemplate;
    }
}
