package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.client.core.QueryParameters;
import com.spotify.docker.client.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

@Repository
public class ContainerLogsClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    @Autowired
    public ContainerLogsClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    LogStream getContainerLogStream(final String containerId, final QueryParameters filters) {
        String containerLogEndpoint = this.endpointBuilder.getContainerLogsEndpoint(containerId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(containerLogEndpoint).queryParams(filters.asQueryParameters());

        return this.restTemplate.getForObject(builder.toUriString(), LogStream.class);
    }
}
