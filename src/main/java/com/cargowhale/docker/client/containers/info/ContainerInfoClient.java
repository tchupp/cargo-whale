package com.cargowhale.docker.client.containers.info;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.client.core.QueryParameters;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.spotify.docker.client.messages.TopResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ContainerInfoClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    @Autowired
    public ContainerInfoClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    public ContainerLogs getContainerLogs(final String containerId, final QueryParameters filters) {
        String containerLogEndpoint = this.endpointBuilder.getContainerLogsEndpoint(containerId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(containerLogEndpoint).queryParams(filters.asQueryParameters());

        return new ContainerLogs(containerId, this.restTemplate.getForObject(builder.toUriString(), String.class));
    }

    public TopResults getContainerProcesses(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerProcessesEndpoint(containerId);
        return this.restTemplate.getForObject(containerByIdEndpoint, TopResults.class);
    }

    public ContainerStats getContainerStats(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerStatsEndpoint(containerId);
        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerStats.class);
    }
}
