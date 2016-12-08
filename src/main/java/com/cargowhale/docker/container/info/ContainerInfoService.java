package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.top.ContainerTopResponse;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.container.info.top.ContainerProcessIndex;
import com.cargowhale.docker.container.info.top.ContainerProcessIndexBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContainerInfoService {

    private final ContainerInfoClient client;
    private final ContainerIndexBuilder containerIndexBuilder;
    private final ContainerProcessIndexBuilder processIndexBuilder;

    @Autowired
    public ContainerInfoService(final ContainerInfoClient client, final ContainerIndexBuilder containerIndexBuilder, final ContainerProcessIndexBuilder processIndexBuilder) {
        this.client = client;
        this.containerIndexBuilder = containerIndexBuilder;
        this.processIndexBuilder = processIndexBuilder;
    }

    ContainerIndex getAllContainers() {
        List<ContainerSummary> containerSummaryList = this.client.getAllContainers();
        return this.containerIndexBuilder.buildContainerIndex(containerSummaryList);
    }

    ContainerIndex getContainersFilterByStatus(final StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerSummary> containerSummaryList = this.client.getFilteredContainers(dockerContainerFilters);
        return this.containerIndexBuilder.buildContainerIndex(containerSummaryList);
    }

    ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogsById(containerId, logFilters);
    }

    ContainerProcessIndex getContainerProcessesById(final String containerId) {
        ContainerTopResponse dockerIndex = this.client.getContainerProcessesById(containerId);
        return this.processIndexBuilder.buildProcessIndex(containerId, dockerIndex);
    }
}
