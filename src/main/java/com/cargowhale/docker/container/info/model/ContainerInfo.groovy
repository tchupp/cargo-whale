package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.container.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerInfo {

    final String id
    final List<String> names
    final String image
    final ContainerState state

    ContainerInfo(@JsonProperty("Id") final String id,
                  @JsonProperty("Names") final List<String> names,
                  @JsonProperty("Image") final String image,
                  @JsonProperty("State") final ContainerState state) {
        this.id = id
        this.names = names
        this.image = image
        this.state = state
    }
}
