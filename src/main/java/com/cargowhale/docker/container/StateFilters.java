package com.cargowhale.docker.container;

public class StateFilters {

    private ContainerState[] state;

    public StateFilters() {
    }

    public StateFilters(final ContainerState[] state) {
        this.state = state;
    }

    public ContainerState[] getState() {
        return state;
    }

    public void setState(final ContainerState[] state) {
        this.state = state;
    }
}
