package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerSummaryIndex {

    final List<ContainerSummary> containers

    ContainerSummaryIndex(@JsonProperty("containers") final List<ContainerSummary> containers) {
        this.containers = containers
    }
}
