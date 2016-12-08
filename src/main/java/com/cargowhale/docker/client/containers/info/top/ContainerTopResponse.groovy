package com.cargowhale.docker.client.containers.info.top

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerTopResponse {

    @JsonProperty("Titles")
    List<String> titles

    @JsonProperty("Processes")
    List<List<String>> processes
}
