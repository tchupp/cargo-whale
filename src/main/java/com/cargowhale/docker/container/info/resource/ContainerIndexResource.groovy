package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.ContainerState
import org.springframework.hateoas.ResourceSupport

class ContainerIndexResource extends ResourceSupport {

    final List<ContainerSummaryResource> containers
    final Map<ContainerState, Integer> stateMetadata

    ContainerIndexResource(
            final List<ContainerSummaryResource> containers,
            final Map<ContainerState, Integer> stateMetadata) {
        this.containers = containers
        this.stateMetadata = stateMetadata
    }
}
