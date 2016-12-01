package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.model.ContainerLogs
import org.springframework.hateoas.Resource

class ContainerLogsResource extends Resource<ContainerLogs> {

    ContainerLogsResource(final ContainerLogs content) {
        super(content)
    }
}
