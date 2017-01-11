package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.spotify.docker.client.DockerClient.ListContainersParam.filter;

@Service
public class ContainerIndexService {

    private final ListContainersClient client;
    private final ContainerIndexBuilder builder;

    public ContainerIndexService(final ListContainersClient client, final ContainerIndexBuilder builder) {
        this.client = client;
        this.builder = builder;
    }

    ContainerIndexResource getContainerIndex() {
        List<Container> containers = this.client.listContainers();
        return this.builder.buildContainerIndex(containers);
    }

    ContainerIndexResource getContainerIndex(final ContainerState[] filters) {
        List<Container> containers = this.client.listContainers(toContainerParams(filters));
        return this.builder.buildContainerIndex(containers);
    }

    private static ListContainersParam[] toContainerParams(final ContainerState[] filters) {
        ListContainersParam[] params = new ListContainersParam[filters.length];
        for (int i = 0; i < filters.length; i++) {
            params[i] = filter("status", filters[i].getState());
        }
        return params;
    }
}
