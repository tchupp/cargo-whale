package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerEndpointBuilder {

    private static final String API_VERSION = "/v1.24";
    private static final String CONTAINERS_ENDPOINT = "/containers";
    private static final String JSON = "/json";

    private final CargoWhaleProperties properties;

    @Autowired
    public DockerEndpointBuilder(final CargoWhaleProperties properties) {
        this.properties = properties;
    }

    public String getContainersEndpoint() {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + JSON;
    }

    public String getContainerByIdEndpoint(final String containerId) {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + "/" + containerId + JSON;
    }
}
