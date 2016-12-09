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
    private final ContainerIndexBuilder builder;

    @Autowired
    public ContainerInfoService(final ContainerInfoClient client, final ContainerIndexBuilder builder) {
        this.client = client;
        this.builder = builder;
    }

    ContainerIndex getAllContainers() {
        List<ContainerSummary> containerSummaryList = this.client.getAllContainers();
        return this.builder.buildContainerIndex(containerSummaryList);
    }

    ContainerIndex getContainersFilterByStatus(final StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerSummary> containerSummaryList = this.client.getFilteredContainers(dockerContainerFilters);
        return this.builder.buildContainerIndex(containerSummaryList);
    }

    ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogsById(containerId, logFilters);
    }
}
