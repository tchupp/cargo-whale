package com.cargowhale.docker.container.info.resource

import com.cargowhale.docker.container.info.model.ContainerDetails
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.hateoas.ResourceSupport

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

class ContainerDetailsResource extends ResourceSupport {

    @JsonProperty("state")
    ContainerDetailsStateResource state

    @JsonProperty(value = "id", access = WRITE_ONLY)
    String containerId

    @JsonProperty("name")
    String name

    @JsonProperty("image")
    String image

    @JsonProperty("path")
    String path

    ContainerDetailsResource(ContainerDetails entity) {
        this.state = new ContainerDetailsStateResource(entity.state)
        this.containerId = entity.id
        this.name = entity.name
        this.image = entity.image
        this.path = entity.path
    }
}
