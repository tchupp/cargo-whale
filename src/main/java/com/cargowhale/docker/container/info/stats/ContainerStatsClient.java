package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.ContainerStats;
import org.springframework.stereotype.Repository;

@Repository
public class ContainerStatsClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    public ContainerStatsClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    ContainerStats getContainerStats(final String containerId) {
        String containerStatsEndpoint = this.endpointBuilder.getContainerStatsEndpoint(containerId);
        return this.restTemplate.getForObject(containerStatsEndpoint, ContainerStats.class);
    }
}
