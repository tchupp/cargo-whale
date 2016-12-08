package com.cargowhale.docker.client.containers.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.DockerEndpointBuilder;
import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.client.containers.info.top.ContainerTopResponse;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerInfoClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;
    private final JsonConverter converter;

    @Autowired
    public ContainerInfoClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder, final JsonConverter converter) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
        this.converter = converter;
    }

    public List<ContainerSummary> getAllContainers() {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerSummary[].class);
        return Arrays.asList(containerSummaryArray);
    }

    public List<ContainerSummary> getFilteredContainers(final DockerContainerFilters filters) {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();
        String filterJson = this.converter.toJson(filters);

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerSummary[].class, filterJson);
        return Arrays.asList(containerSummaryArray);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerInfoByIdEndpoint(containerId);

        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerDetails.class);
    }

    public ContainerLogs getContainerLogsById(final String containerId, final LogFilters filters) {
        String containerLogEndpoint = this.endpointBuilder.getContainerLogByIdEndpoint(containerId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(containerLogEndpoint)
                .queryParam("details", filters.getDetails())
                .queryParam("follow", filters.getFollow())
                .queryParam("stdout", filters.getStdout())
                .queryParam("stderr", filters.getStderr())
                .queryParam("timestamps", filters.getTimestamps())
                .queryParam("since", filters.getSince())
                .queryParam("tail", filters.getTail());

        return new ContainerLogs(containerId, this.restTemplate.getForObject(builder.toUriString(), String.class));
    }

    public ContainerTopResponse getContainerProcessesById(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId);
        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerTopResponse.class);
    }
}
