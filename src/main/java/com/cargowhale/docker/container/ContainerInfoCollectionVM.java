package com.cargowhale.docker.container;

import java.util.List;

public class ContainerInfoCollectionVM {

    private List<ContainerInfoVM> containers;

    public ContainerInfoCollectionVM() {
    }

    public ContainerInfoCollectionVM(final List<ContainerInfoVM> containers) {
        this.containers = containers;
    }

    public List<ContainerInfoVM> getContainers() {
        return containers;
    }

    public void setContainers(final List<ContainerInfoVM> containers) {
        this.containers = containers;
    }
}
