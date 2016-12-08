package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerIndex {

    final List<ContainerListItem> containers
    final Map<ContainerState, Integer> stateMetadata

    ContainerIndex(
            @JsonProperty("containers") final List<ContainerListItem> containers,
            @JsonProperty("stateMetadata") final Map<ContainerState, Integer> stateMetadata) {
        this.containers = containers
        this.stateMetadata = stateMetadata
    }
}
