package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.model.ContainerSummary;
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

    ContainerIndex getAllContainers() {
        List<ContainerSummary> allContainers = this.client.getAllContainers();
        return new ContainerIndex(allContainers);
    }

    ContainerIndex getContainersFilterByStatus(final StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerSummary> filteredContainers = this.client.getFilteredContainers(dockerContainerFilters);
        return new ContainerIndex(filteredContainers);
    }

    ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogsById(containerId, logFilters);
    }
}
