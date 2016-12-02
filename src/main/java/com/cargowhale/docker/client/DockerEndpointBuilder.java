package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DockerEndpointBuilder {

    private static final String API_VERSION = "/v1.24";
    private static final String CONTAINERS_ENDPOINT = "/containers";
    private static final String JSON = "/json";
    private static final String PROCESSES = "/top";
    private static final String LOGS = "/logs";

    private final CargoWhaleProperties properties;

    @Autowired
    public DockerEndpointBuilder(final CargoWhaleProperties properties) {
        this.properties = properties;
    }

    public String getContainersInfoEndpoint() {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + JSON;
    }

    public String getContainerInfoByIdEndpoint(final String containerId) {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + "/" + containerId + JSON;
    }

    public String setContainerByIdEndpoint(final String containerId) {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + "/" + containerId + "/";
    }

    public String getContainerLogByIdEndpoint(final String containerId) {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + "/" + containerId + LOGS;
    }

    public String getContainerProcessesByIdEndpoint(final String containerId) {
        return this.properties.getDockerUri() + API_VERSION + CONTAINERS_ENDPOINT + "/" + containerId + PROCESSES;
    }
}
