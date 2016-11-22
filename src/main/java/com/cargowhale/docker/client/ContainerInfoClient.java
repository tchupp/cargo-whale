package com.cargowhale.docker.client;

import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

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
        String containersEndpoint = this.endpointBuilder.getContainersEndpoint();

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerSummary[].class);
        return Arrays.asList(containerSummaryArray);
    }

    public List<ContainerSummary> getFilteredContainers(DockerContainerFilters filters) {
        String containersEndpoint = this.endpointBuilder.getContainersEndpoint();
        String filterJson = this.converter.toJson(filters);

        ContainerSummary[] containerSummaryArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerSummary[].class, filterJson);
        return Arrays.asList(containerSummaryArray);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerByIdEndpoint(containerId);

        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerDetails.class);
    }
}
