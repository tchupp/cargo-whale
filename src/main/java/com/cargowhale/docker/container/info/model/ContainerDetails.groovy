package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@Canonical
class ContainerDetails {

    @JsonProperty("State")
    ContainerDetailsState state

    @JsonProperty(value = "Id", access = WRITE_ONLY)
    String id

    @JsonProperty("Name")
    String name

    @JsonProperty("Image")
    String image

    @JsonProperty("Path")
    String path

    ContainerState getContainerState() {
        return this.state.state
    }
}
