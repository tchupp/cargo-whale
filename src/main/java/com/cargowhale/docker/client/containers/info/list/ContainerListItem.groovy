package com.cargowhale.docker.client.containers.info.list

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerListItem {

    @JsonProperty("State")
    ContainerState state

    @JsonProperty("Id")
    String id

    @JsonProperty("Command")
    String command

    @JsonProperty("Image")
    String image

    @JsonProperty("ImageID")
    String imageId

    @JsonProperty("Names")
    String[] names

    @JsonProperty("Status")
    String status

    @JsonProperty("Ports")
    ContainerPort[] ports
}
