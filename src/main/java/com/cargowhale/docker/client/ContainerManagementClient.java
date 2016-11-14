package com.cargowhale.docker.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class ContainerManagementClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    @Autowired
    public ContainerManagementClient(final RestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    //Status options are: [start, stop, restart, kill]
    public String setContainerStatus(String name, String status) {
        String containersEndpoint = this.endpointBuilder.getContainersEndpoint();

        this.restTemplate.postForObject(containersEndpoint + "/{name}/{status}", null, String.class, name, status);
        return name;
    }
}
