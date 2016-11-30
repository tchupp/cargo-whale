package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.model.ContainerProcesses
import org.springframework.hateoas.Resource

class ContainerProcessesResource extends Resource<ContainerProcesses> {
    ContainerProcessesResource(final ContainerProcesses process) {
        super(process)
    }
}
