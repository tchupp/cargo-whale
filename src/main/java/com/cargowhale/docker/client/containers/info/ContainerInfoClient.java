package com.cargowhale.docker.client.containers.info;

import com.cargowhale.docker.client.DockerEndpointBuilder;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerInfoClient {

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    @Autowired
    public ContainerInfoClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    public List<ContainerListItem> listContainers() {
        String containersEndpoint = this.endpointBuilder.getListContainersEndpoint();

        ContainerListItem[] containerArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerListItem[].class);
        return Arrays.asList(containerArray);
    }

    public List<ContainerListItem> listContainers(final ListContainerFilters filters) {
        String containersEndpoint = this.endpointBuilder.getListContainersEndpoint();

        ContainerListItem[] containerArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerListItem[].class, filters.toJson());
        return Arrays.asList(containerArray);
    }

    public ContainerDetails inspectContainer(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getInspectContainerEndpoint(containerId);

        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerDetails.class);
    }

    public ContainerLogs getContainerLogs(final String containerId, final LogFilters filters) {
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

    public ContainerTop getContainerProcesses(final String containerId) {
        String containerByIdEndpoint = this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId);
        return this.restTemplate.getForObject(containerByIdEndpoint, ContainerTop.class);
    }
}
