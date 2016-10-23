package com.cargowhale.docker.service;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContainerService {

    private final CargoWhaleProperties properties;
    private final RestTemplate restTemplate;

    @Autowired
    public ContainerService(final RestTemplate restTemplate, final CargoWhaleProperties properties) {
        this.restTemplate = restTemplate;
        this.properties = properties;
    }

    public String getAllContainers() {
        String dockerUri = this.properties.getDockerUri();
        return this.restTemplate.getForObject(dockerUri + "/containers/json?all=1", String.class);
    }
}
