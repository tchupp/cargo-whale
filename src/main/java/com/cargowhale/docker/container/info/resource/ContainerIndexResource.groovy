package com.cargowhale.docker.container.info.resource

import org.springframework.hateoas.ResourceSupport

class ContainerIndexResource extends ResourceSupport {

    final List<ContainerSummaryResource> containers

    ContainerIndexResource(final List<ContainerSummaryResource> containers) {
        this.containers = containers
    }
}
