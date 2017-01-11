package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Repository;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static com.spotify.docker.client.DockerClient.ListContainersParam.allContainers;

@Repository
public class ListContainersClient {

    private static final ParameterizedTypeReference<List<Container>> LIST_CONTAINER_TYPE = new ParameterizedTypeReference<List<Container>>() {
    };

    private final DockerRestTemplate restTemplate;
    private final DockerEndpointBuilder endpointBuilder;

    @Autowired
    public ListContainersClient(final DockerRestTemplate restTemplate, final DockerEndpointBuilder endpointBuilder) {
        this.restTemplate = restTemplate;
        this.endpointBuilder = endpointBuilder;
    }

    List<Container> listContainers() {
        return listContainers(allContainers());
    }

    List<Container> listContainers(final ListContainersParam... params) {
        String listContainersEndpoint = this.endpointBuilder.getListContainersEndpoint();

        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(listContainersEndpoint);
        for (final ListContainersParam param : params) {
            builder.queryParam(param.name(), param.value());
        }

        return this.restTemplate.getForObject(builder.toUriString(), LIST_CONTAINER_TYPE);
    }
}
