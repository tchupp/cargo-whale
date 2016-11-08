package com.cargowhale.docker.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContainerManagementClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointCollection endpointCollection;

    @Autowired
    public ContainerManagementClient(final RestTemplate restTemplate, final DockerEndpointCollection endpointCollection) {
        this.restTemplate = restTemplate;
        this.endpointCollection = endpointCollection;
    }

    //Status options are: [start, stop, restart, kill]
    public String setContainerStatus(String name, String status) {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        this.restTemplate.postForObject(containersEndpoint + "/{name}/{status}", null, String.class, name, status);
        return name;
    }
}
