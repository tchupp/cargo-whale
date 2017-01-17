package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.core.exception.BadContainerStateException;
import com.cargowhale.docker.container.info.details.InspectContainerClient;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.TopResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerTopService {

    private final ContainerInfoClient infoClient;
    private final InspectContainerClient inspectContainerClient;
    private final ContainerProcessIndexBuilder processIndexBuilder;

    @Autowired
    public ContainerTopService(final ContainerInfoClient infoClient, final InspectContainerClient inspectContainerClient, final ContainerProcessIndexBuilder processIndexBuilder) {
        this.infoClient = infoClient;
        this.inspectContainerClient = inspectContainerClient;
        this.processIndexBuilder = processIndexBuilder;
    }

    public ContainerProcessIndex getContainerProcessesById(final String containerId) {
        ContainerInfo containerInfo = this.inspectContainerClient.inspectContainer(containerId);
        if (!containerInfo.state().running()) {
            throw new BadContainerStateException(containerInfo.state().status());
        }

        TopResults results = this.infoClient.getContainerProcesses(containerId);
        return this.processIndexBuilder.buildProcessIndex(containerId, results);
    }
}
