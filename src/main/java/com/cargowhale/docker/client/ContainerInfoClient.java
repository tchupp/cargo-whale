package com.cargowhale.docker.client;

import com.cargowhale.docker.container.LogFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerInfoClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;
    private final JsonConverter converter;

    @Autowired
    public ContainerInfoClient(final RestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder, final JsonConverter converter) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
        this.converter = converter;
    }

    public List<ContainerSummary> getAllContainers() {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerSummary[].class);
        return Arrays.asList(containerSummaryArray);
    }

    public List<ContainerSummary> getFilteredContainers(DockerContainerFilters filters) {
        String containersEndpoint = this.endpointBuilder.getContainersInfoEndpoint();
        String filterJson = this.converter.toJson(filters);

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerSummary[].class, filterJson);
        return Arrays.asList(containerSummaryArray);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerInfoByIdEndpoint(containerId);

        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerDetails.class);
    }

    public String getContainerLogsById(final String containerId, final LogFilters filters) {
        String containerLogEndpoint = this.endpointBuilder.getContainerLogByIdEndpoint(containerId);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(containerLogEndpoint)
                .queryParam("follow", filters.getFollow())
                .queryParam("stdout", filters.getStdout())
                .queryParam("stderr", filters.getStderr())
                .queryParam("since", filters.getSince())
                .queryParam("timestamps", filters.getTimestamps())
                .queryParam("tail", filters.getTail());

        return this.restTemplate.getForObject(builder.toUriString(), String.class);
    }
}
