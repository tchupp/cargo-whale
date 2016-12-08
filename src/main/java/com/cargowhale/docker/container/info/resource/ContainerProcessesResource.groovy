package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.top.ContainerProcessIndex
import org.springframework.hateoas.Resource

class ContainerProcessesResource extends Resource<ContainerProcessIndex> {
    ContainerProcessesResource(final ContainerProcessIndex process) {
        super(process)
    }
}
