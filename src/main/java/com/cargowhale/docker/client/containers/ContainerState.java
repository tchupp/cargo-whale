package com.cargowhale.docker.client.containers;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ContainerState {

    @JsonProperty("created")
    CREATED("created"),

    @JsonProperty("restarting")
    RESTARTING("restarting"),

    @JsonProperty("running")
    RUNNING("running"),

    @JsonProperty("paused")
    PAUSED("paused"),

    @JsonProperty("exited")
    EXITED("exited"),

    @JsonProperty("dead")
    DEAD("dead");

    public final String state;

    ContainerState(final String state) {
        this.state = state;
    }
}
