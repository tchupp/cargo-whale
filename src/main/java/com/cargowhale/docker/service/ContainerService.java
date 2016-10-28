package com.cargowhale.docker.service;

import com.cargowhale.docker.client.ContainerClient;
import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerService {

    private final ContainerClient client;

    @Autowired
    public ContainerService(final ContainerClient client) {
        this.client = client;
    }

    public String getFilteredContainers(String filter) {
        return this.client.getFilteredContainers(filter);
    }

    public ContainerInfoCollectionVM getAllContainers() {
        return new ContainerInfoCollectionVM(this.client.getAllContainers());
    }

    public ChangeStatusResponse setContainerStatus(String name, ChangeStatusRequest statusRequest) {
        String newStatus = statusRequest.getStatus();
        String containerName = this.client.setContainerStatus(name, newStatus);

        return new ChangeStatusResponse(containerName);
    }
}
