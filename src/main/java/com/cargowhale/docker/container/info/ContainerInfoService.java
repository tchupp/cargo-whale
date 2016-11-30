package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.LogFilters;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ContainerInfoService {

    private final ContainerInfoClient client;

    @Autowired
    public ContainerInfoService(final ContainerInfoClient client) {
        this.client = client;
    }

    public ContainerSummaryIndex getAllContainers() {
        List<ContainerSummary> allContainers = this.client.getAllContainers();
        return new ContainerSummaryIndex(allContainers);
    }

    public ContainerSummaryIndex getContainersFilterByStatus(final StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerSummary> filteredContainers = this.client.getFilteredContainers(dockerContainerFilters);
        return new ContainerSummaryIndex(filteredContainers);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }

    public ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogsById(containerId, logFilters);
    }

    public ContainerProcesses getContainerProcessesById(String id) {
        return null;
    }
}
