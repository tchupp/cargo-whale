package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerIndex {

    final List<ContainerSummary> containers

    ContainerIndex(@JsonProperty("containers") final List<ContainerSummary> containers) {
        this.containers = containers
    }
}
