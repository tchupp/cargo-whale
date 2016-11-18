package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerDetails {

    final String id
    final String name

    ContainerDetails(@JsonProperty("Id") final String id,
                     @JsonProperty("Name") final String name) {
        this.id = id
        this.name = name
    }
}
