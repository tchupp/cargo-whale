package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.container.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerSummary {

    final String containerId
    final List<String> names
    final String image
    final ContainerState state

    ContainerSummary(@JsonProperty("Id") final String containerId,
                     @JsonProperty("Names") final List<String> names,
                     @JsonProperty("Image") final String image,
                     @JsonProperty("State") final ContainerState state) {
        this.containerId = containerId
        this.names = names
        this.image = image
        this.state = state
    }
}
