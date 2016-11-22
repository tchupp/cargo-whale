package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerInfoCollection {

    final List<ContainerInfo> containers

    ContainerInfoCollection(@JsonProperty("containers") final List<ContainerInfo> containers) {
        this.containers = containers
    }
}
