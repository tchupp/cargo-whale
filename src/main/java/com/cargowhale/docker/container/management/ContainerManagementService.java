package com.cargowhale.docker.container.management;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.domain.ChangeStateRequest;
import com.cargowhale.docker.domain.ChangeStateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerManagementService {

    private final ContainerInfoClient client;

    @Autowired
    public ContainerManagementService(final ContainerInfoClient client) {
        this.client = client;
    }

    public ChangeStateResponse changeContainerState(String name, ChangeStateRequest statusRequest) {
        String newStatus = statusRequest.getStatus();
        String containerName = this.client.setContainerStatus(name, newStatus);

        return new ChangeStateResponse(containerName);
    }
}
