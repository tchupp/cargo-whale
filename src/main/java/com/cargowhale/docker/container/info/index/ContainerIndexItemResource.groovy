package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport

@Canonical
class ContainerIndexItemResource extends ResourceSupport {

    ContainerState state
    @JsonProperty("id")
    String containerId
    String image
    String imageId
    String name
    String status
    ContainerPortResource[] ports

    ContainerIndexItemResource(ContainerListItem entity) {
        this.state = entity.state
        this.containerId = entity.id
        this.image = entity.image
        this.imageId = entity.imageId
        this.name = entity.names?.join(' ')
        this.status = entity.status
        this.ports = entity.ports.collect { new ContainerPortResource(it) }
    }
}