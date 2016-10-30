package com.cargowhale.docker.client;

import com.cargowhale.docker.container.ContainerStatus;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

public class DockerContainerFilters {

    private final Set<ContainerStatus> status;

    public DockerContainerFilters(final Set<ContainerStatus> status) {
        this.status = status;
    }

    public DockerContainerFilters(final ContainerStatus[] status) {
        this.status = new LinkedHashSet<>(Arrays.asList(status));
    }

    public Set<ContainerStatus> getStatus() {
        return this.status;
    }
}
