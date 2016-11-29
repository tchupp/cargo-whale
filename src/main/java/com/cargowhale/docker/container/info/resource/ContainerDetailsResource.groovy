package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.model.ContainerDetails
import org.springframework.hateoas.Resource

class ContainerDetailsResource extends Resource<ContainerDetails> {

    ContainerDetailsResource(final ContainerDetails content) {
        super(content)
    }
}
