package com.cargowhale.docker.container.info.model

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerDetailsState {

    final int pid
    final ContainerState status
    final String error
    final int exitCode
    final String finishedAt

    ContainerDetailsState(
            @JsonProperty("Pid") final int pid,
            @JsonProperty("Status") final ContainerState status,
            @JsonProperty("Error") final String error,
            @JsonProperty("ExitCode") final int exitCode,
            @JsonProperty("FinishedAt") final String finishedAt) {
        this.pid = pid
        this.status = status
        this.error = error
        this.exitCode = exitCode
        this.finishedAt = finishedAt
    }
}
