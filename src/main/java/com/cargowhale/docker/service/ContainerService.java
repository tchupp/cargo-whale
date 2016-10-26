package com.cargowhale.docker.service;

import com.cargowhale.docker.client.ContainerClient;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerService {

    @Autowired
    private ContainerClient client;

    public String getFilteredContainers(String filter) {
        return this.client.getFilteredContainers(filter);
    }

    public String getAllContainers() {
        return this.client.getAllContainers();
    }

    public ChangeStatusResponse setContainerStatus(String name, ChangeStatusRequest statusRequest) {
        return new ChangeStatusResponse().setName(this.client.setContainerStatus(name, statusRequest.getStatus()));
    }
}
