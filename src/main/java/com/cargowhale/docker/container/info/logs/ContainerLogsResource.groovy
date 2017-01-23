package com.cargowhale.docker.container.info.logs

import org.springframework.hateoas.Resource

class ContainerLogsResource extends Resource<ContainerLogs> {

    ContainerLogsResource(final ContainerLogs content) {
        super(content)
    }
}
