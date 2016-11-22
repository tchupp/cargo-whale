package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.model.ContainerSummary
import org.springframework.hateoas.Resource

class ContainerSummaryResource extends Resource<ContainerSummary> {

    ContainerSummaryResource(final ContainerSummary content) {
        super(content)
    }
}
