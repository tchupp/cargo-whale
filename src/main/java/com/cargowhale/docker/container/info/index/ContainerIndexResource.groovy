package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import org.springframework.hateoas.ResourceSupport

class ContainerIndexResource extends ResourceSupport {

    final List<ContainerListItemResource> containers
    final Map<ContainerState, Integer> stateMetadata

    ContainerIndexResource(
            final List<ContainerListItemResource> containers,
            final Map<ContainerState, Integer> stateMetadata) {
        this.containers = containers
        this.stateMetadata = stateMetadata
    }
}
