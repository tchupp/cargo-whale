package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContainerIndexService {

    private final ListContainersClient client;
    private final ContainerIndexBuilder builder;

    public ContainerIndexService(final ListContainersClient client, final ContainerIndexBuilder builder) {
        this.client = client;
        this.builder = builder;
    }

    ContainerIndexResource getContainerIndex(final ListContainersParam... filters) {
        List<Container> containers = this.client.listContainers(filters);
        return this.builder.buildContainerIndex(containers);
    }
}
