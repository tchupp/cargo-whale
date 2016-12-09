package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import org.springframework.hateoas.Resource

class ContainerListResponseItemResource extends Resource<ContainerListItem> {

    ContainerListResponseItemResource(final ContainerListItem content) {
        super(content)
    }
}
