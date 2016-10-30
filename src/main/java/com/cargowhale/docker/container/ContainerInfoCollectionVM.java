package com.cargowhale.docker.container;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContainerInfoCollectionVM {

    private final List<ContainerInfoVM> containers;

    public ContainerInfoCollectionVM(@JsonProperty("containers") final List<ContainerInfoVM> containers) {
        this.containers = containers;
    }

    public List<ContainerInfoVM> getContainers() {
        return containers;
    }
}
