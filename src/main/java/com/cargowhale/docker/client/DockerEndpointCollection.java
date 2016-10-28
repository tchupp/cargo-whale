package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerEndpointCollection {

    private static final String CONTAINERS_ENDPOINT = "/v1.24/containers/json";

    private final CargoWhaleProperties properties;

    @Autowired
    public DockerEndpointCollection(final CargoWhaleProperties properties) {
        this.properties = properties;
    }

    public String getContainersEndpoint() {
        return this.properties.getDockerUri() + CONTAINERS_ENDPOINT;
    }
}
