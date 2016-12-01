package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@ToString
class ContainerProcessIndex {

    @JsonProperty(access = WRITE_ONLY)
    final String id;
    List<ContainerProcess> processes;

    ContainerProcessIndex(final String id, List<ContainerProcess> processes) {
        this.id = id;
        this.processes = processes;
    }
}
