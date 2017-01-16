package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerIndexService {

    private final ListContainersClient client;
    private final ContainerMapper mapper;

    public ContainerIndexService(final ListContainersClient client, final ContainerMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    List<ContainerResource> getContainerIndex(final ListContainersParam... filters) {
        List<Container> containers = this.client.listContainers(filters);
        return this.mapper.toResources(containers);
    }
}
