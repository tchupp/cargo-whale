package com.cargowhale.docker.client

import com.cargowhale.docker.container.ContainerState
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class DockerContainerFilters {

    final Set<ContainerState> status

    public DockerContainerFilters(final ContainerState[] status) {
        this.status = new LinkedHashSet<>(Arrays.asList(status))
    }
}
