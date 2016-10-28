package com.cargowhale.docker.container;

public enum ContainerState {

    CREATED("created"),
    RESTARTING("restarting"),
    RUNNING("running"),
    PAUSED("paused"),
    EXITED("exited"),
    DEAD("dead");

    public final String state;

    ContainerState(final String state) {
        this.state = state;
    }
}
