package com.cargowhale.docker.client.containers.info.inspect

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerDetails {

    @JsonProperty("State")
    ContainerDetailsState state

    @JsonProperty("Config")
    ContainerDetailsConfig config

    @JsonProperty("NetworkSettings")
    ContainerNetworkSettings networkSettings

    @JsonProperty("Id")
    String id

    @JsonProperty("Name")
    String name

    @JsonProperty("Image")
    String image

    @JsonProperty("Path")
    String path

    @JsonProperty("Created")
    String created

    @JsonProperty("Args")
    String[] args

    @JsonProperty("RestartCount")
    Integer restartCount

    @JsonProperty("Driver")
    String driver

    ContainerState getContainerState() {
        return this.state.state
    }
}
