package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.container.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerIndex {

    final List<ContainerSummary> containers
    final Map<ContainerState, Integer> stateMetadata

    ContainerIndex(
            @JsonProperty("containers") final List<ContainerSummary> containers,
            @JsonProperty("stateMetadata") final Map<ContainerState, Integer> stateMetadata) {
        this.containers = containers
        this.stateMetadata = stateMetadata
    }
}
