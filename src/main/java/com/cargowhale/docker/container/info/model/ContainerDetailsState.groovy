package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
@Canonical
class ContainerDetailsState {

    @JsonProperty("Status")
    ContainerState state

    @JsonProperty("Pid")
    Integer pid

    @JsonProperty("Error")
    String error

    @JsonProperty("ExitCode")
    Integer exitCode

    @JsonProperty("FinishedAt")
    String finishedAt
}
