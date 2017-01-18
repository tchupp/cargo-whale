package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.core.exception.BadContainerStateException;
import com.cargowhale.docker.container.info.details.InspectContainerClient;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.TopResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerProcessService {

    private final ContainerTopClient topClient;
    private final InspectContainerClient inspectContainerClient;
    private final ContainerProcessIndexBuilder processIndexBuilder;

    @Autowired
    public ContainerProcessService(final ContainerTopClient topClient, final InspectContainerClient inspectContainerClient, final ContainerProcessIndexBuilder processIndexBuilder) {
        this.topClient = topClient;
        this.inspectContainerClient = inspectContainerClient;
        this.processIndexBuilder = processIndexBuilder;
    }

    ContainerProcessIndex getContainerProcesses(final String containerId) {
        ContainerInfo containerInfo = this.inspectContainerClient.inspectContainer(containerId);
        if (!containerInfo.state().running()) {
            throw new BadContainerStateException(containerInfo.state().status());
        }

        TopResults results = this.topClient.containerTop(containerId);
        return this.processIndexBuilder.buildProcessIndex(containerId, results);
    }
}
