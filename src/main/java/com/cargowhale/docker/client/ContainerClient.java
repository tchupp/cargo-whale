package com.cargowhale.docker.client;

import com.cargowhale.docker.container.ContainerInfoVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointCollection endpointCollection;

    @Autowired
    public ContainerClient(final RestTemplate restTemplate, final DockerEndpointCollection endpointCollection) {
        this.restTemplate = restTemplate;
        this.endpointCollection = endpointCollection;
    }

    public List<ContainerInfoVM> getAllContainers() {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        ContainerInfoVM[] containerInfoArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerInfoVM[].class);
        return Arrays.asList(containerInfoArray);
    }

    public List<ContainerInfoVM> getFilteredContainers(DockerContainerFilters filters) {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        ContainerInfoVM[] containerInfoArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerInfoVM[].class, filters);
        return Arrays.asList(containerInfoArray);
    }

    //Status options are: [start, stop, restart, kill]
    public String setContainerStatus(String name, String status) {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        this.restTemplate.postForObject(containersEndpoint + "/{name}/{status}", null, String.class, name, status);
        return name;
    }
}
