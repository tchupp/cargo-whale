package com.cargowhale.docker.client.containers.management.state;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContainerChangeState {

    @JsonProperty("start")
    START("start"),

    @JsonProperty("stop")
    STOP("stop"),

    @JsonProperty("restart")
    RESTART("restart"),

    @JsonProperty("kill")
    KILL("kill");

    public final String state;

    ContainerChangeState(final String state) {
        this.state = state;
    }
}
