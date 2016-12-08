package com.cargowhale.docker.client.containers.info.top

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerTop {

    @JsonProperty("Processes")
    List<List<String>> processes

    @JsonProperty("Titles")
    List<String> titles
}
