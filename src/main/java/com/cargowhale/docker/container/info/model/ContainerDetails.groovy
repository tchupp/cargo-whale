package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@ToString
class ContainerDetails {

    @JsonProperty(access = WRITE_ONLY)
    final String id
    final String name
    final String image
    final String path
    final ContainerDetailsState state

    ContainerDetails(@JsonProperty("Id") final String id,
                     @JsonProperty("Name") final String name,
                     @JsonProperty("Image") final String image,
                     @JsonProperty("Path") final String path,
                     @JsonProperty("State") final ContainerDetailsState state) {
        this.id = id
        this.name = name
        this.image = image
        this.path = path
        this.state = state
    }
}
