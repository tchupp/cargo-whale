package com.cargowhale.docker.config;

import com.cargowhale.docker.client.core.DockerErrorHandler;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfiguration {

    private final CargoWhaleProperties properties;
    private final DockerErrorHandler errorHandler;

    @Autowired
    public RestTemplateConfiguration(final CargoWhaleProperties properties, final DockerErrorHandler errorHandler) {
        this.properties = properties;
        this.errorHandler = errorHandler;
    }

    @Bean
    public DockerRestTemplate dockerRestTemplate() {
        DockerRestTemplate restTemplate = new DockerRestTemplate(this.errorHandler);
        RootUriTemplateHandler.addTo(restTemplate, this.properties.getDockerUri());

        return restTemplate;
    }
}
