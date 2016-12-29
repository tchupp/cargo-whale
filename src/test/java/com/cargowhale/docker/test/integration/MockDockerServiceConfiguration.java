package com.cargowhale.docker.test.integration;

import com.cargowhale.division.MockServiceBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.config.DockerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static com.cargowhale.division.MockServiceBuilder.fromRestTemplate;

@Configuration
public class MockDockerServiceConfiguration {

    private final DockerRestTemplate dockerRestTemplate;
    private final DockerProperties properties;

    @Autowired
    public MockDockerServiceConfiguration(final DockerRestTemplate dockerRestTemplate, final DockerProperties properties) {
        this.dockerRestTemplate = dockerRestTemplate;
        this.properties = properties;
    }

    @Bean
    @Qualifier("dockerServiceBuilder")
    public MockServiceBuilder dockerServiceBuilder() {
        return fromRestTemplate(this.dockerRestTemplate, RamlSpecFiles.DOCKER_RAML_SPEC_FILE, this.properties.getUri());
    }
}
