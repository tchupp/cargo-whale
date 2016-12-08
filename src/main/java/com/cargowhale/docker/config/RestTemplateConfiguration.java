package com.cargowhale.docker.config;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RestTemplateConfiguration {

    private final CargoWhaleProperties properties;

    @Autowired
    public RestTemplateConfiguration(final CargoWhaleProperties properties) {
        this.properties = properties;
    }

    @Bean
    public DockerRestTemplate dockerRestTemplate() {
        DockerRestTemplate restTemplate = new DockerRestTemplate();
        RootUriTemplateHandler.addTo(restTemplate, this.properties.getDockerUri());

        return restTemplate;
    }
}
