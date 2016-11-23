package com.cargowhale.docker.container.info.resource

import org.springframework.hateoas.ResourceSupport

class ContainerSummaryIndexResource extends ResourceSupport {

    final List<ContainerSummaryResource> containers

    ContainerSummaryIndexResource(final List<ContainerSummaryResource> containers) {
        this.containers = containers
    }
}
