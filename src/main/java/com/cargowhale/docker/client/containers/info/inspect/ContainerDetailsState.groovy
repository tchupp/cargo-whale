package com.cargowhale.docker.client.containers.info.inspect

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

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

    @JsonProperty("StartedAt")
    String startedTime

    @JsonProperty("FinishedAt")
    String finishedTime
}
