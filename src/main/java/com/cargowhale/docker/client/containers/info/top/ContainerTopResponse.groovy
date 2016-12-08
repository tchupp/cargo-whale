package com.cargowhale.docker.client.containers.info.top

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerTopResponse {

    @JsonProperty("Processes")
    List<List<String>> processes

    @JsonProperty("Titles")
    List<String> titles
}
