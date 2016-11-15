package com.cargowhale.docker.container;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContainerInfoCollection {

    private final List<ContainerInfoVM> containers;

    public ContainerInfoCollection(@JsonProperty("containers") final List<ContainerInfoVM> containers) {
        this.containers = containers;
    }

    public List<ContainerInfoVM> getContainers() {
        return containers;
    }
}
