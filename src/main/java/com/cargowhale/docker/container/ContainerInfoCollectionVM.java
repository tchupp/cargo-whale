package com.cargowhale.docker.container;

import java.util.List;

public class ContainerInfoCollectionVM {

    private final List<ContainerInfoVM> containers;

    public ContainerInfoCollectionVM(final List<ContainerInfoVM> containers) {
        this.containers = containers;
    }

    public List<ContainerInfoVM> getContainers() {
        return containers;
    }
}
