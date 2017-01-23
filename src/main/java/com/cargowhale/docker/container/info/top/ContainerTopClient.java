package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.TopResults;
import org.springframework.stereotype.Repository;

@Repository
public class ContainerTopClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    public ContainerTopClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    TopResults containerTop(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerProcessesEndpoint(containerId);
        return this.restTemplate.getForObject(containerByIdEndpoint, TopResults.class);
    }
}
