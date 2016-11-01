package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.DockerEndpointCollection;
import com.cargowhale.docker.util.JsonConverter;
import com.cargowhale.docker.container.ContainerInfoVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Component
public class ContainerInfoClient {

    private final RestTemplate restTemplate;
    private final DockerEndpointCollection endpointCollection;
    private final JsonConverter converter;

    @Autowired
    public ContainerInfoClient(final RestTemplate restTemplate, final DockerEndpointCollection endpointCollection, final JsonConverter converter) {
        this.restTemplate = restTemplate;
        this.endpointCollection = endpointCollection;
        this.converter = converter;
    }

    public List<ContainerInfoVM> getAllContainers() {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        ContainerInfoVM[] containerInfoArray = this.restTemplate.getForObject(containersEndpoint + "?all=1", ContainerInfoVM[].class);
        return Arrays.asList(containerInfoArray);
    }

    public List<ContainerInfoVM> getFilteredContainers(DockerContainerFilters filters) {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();
        String filterJson = this.converter.toJson(filters);

        ContainerInfoVM[] containerInfoArray = this.restTemplate.getForObject(containersEndpoint + "?filters={filters}", ContainerInfoVM[].class, filterJson);
        return Arrays.asList(containerInfoArray);
    }

    //Status options are: [start, stop, restart, kill]
    public String setContainerStatus(String name, String status) {
        String containersEndpoint = this.endpointCollection.getContainersEndpoint();

        this.restTemplate.postForObject(containersEndpoint + "/{name}/{status}", null, String.class, name, status);
        return name;
    }
}
