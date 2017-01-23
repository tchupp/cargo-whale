package com.cargowhale.docker.container.management;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerManagementService {

    private final ContainerManagementClient client;

    @Autowired
    public ContainerManagementService(final ContainerManagementClient client) {
        this.client = client;
    }

    ChangeStateResponse changeContainerState(final String name, final ChangeStateRequest request) {
        String containerName = this.client.changeContainerState(name, request.getState());

        return new ChangeStateResponse(containerName);
    }
}
