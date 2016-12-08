package com.cargowhale.docker.client.containers.management;

import com.cargowhale.docker.client.DockerEndpointBuilder;
import com.cargowhale.docker.client.containers.management.state.ContainerChangeState;
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
    public String changeContainerState(final String name, final ContainerChangeState state) {
        String containersEndpoint = this.endpointBuilder.getContainerChangeStateEndpoint(name, state);

        this.restTemplate.postForObject(containersEndpoint, null, String.class, state);
        return name;
    }
}
