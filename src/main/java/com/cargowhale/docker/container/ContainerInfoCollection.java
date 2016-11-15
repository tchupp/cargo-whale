package com.cargowhale.docker.container;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContainerInfoCollection {

    private final List<ContainerInfo> containers;

    public ContainerInfoCollection(@JsonProperty("containers") final List<ContainerInfo> containers) {
        this.containers = containers;
    }

    public List<ContainerInfo> getContainers() {
        return containers;
    }
}
