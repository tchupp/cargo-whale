package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode
import groovy.transform.TupleConstructor
import org.springframework.hateoas.ResourceSupport

@EqualsAndHashCode(excludes = "id")
@TupleConstructor(force = true)
@Canonical
class ContainerIndexItemResource extends ResourceSupport {

    @JsonProperty("state")
    final ContainerState state

    @JsonProperty(value = "id")
    final String containerId

    @JsonProperty("image")
    final String image

    @JsonProperty("imageId")
    final String imageId

    @JsonProperty("name")
    final String name

    @JsonProperty("status")
    final String status

    ContainerIndexItemResource(ContainerListItem entity) {
        this.state = entity.state
        this.containerId = entity.id
        this.image = entity.image
        this.imageId = entity.imageId
        this.name = entity.names?.join(' ')
        this.status = entity.status
    }
}