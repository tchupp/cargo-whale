package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerInfoCollection {

    private final List<ContainerInfo> containers

    ContainerInfoCollection(@JsonProperty("containers") final List<ContainerInfo> containers) {
        this.containers = containers
    }

    List<ContainerInfo> getContainers() {
        return this.containers
    }
}
