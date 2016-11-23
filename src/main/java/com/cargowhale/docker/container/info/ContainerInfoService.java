package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.container.info.model.ContainerSummaryIndex;
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

    public ContainerSummaryIndex getContainersFilterByStatus(StateFilters stateFilters) {
        DockerContainerFilters dockerContainerFilters = new DockerContainerFilters(stateFilters.getState());

        List<ContainerSummary> filteredContainers = this.client.getFilteredContainers(dockerContainerFilters);
        return new ContainerSummaryIndex(filteredContainers);
    }

    public ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.getContainerDetailsById(containerId);
    }

    public String getContainerLogsById(String containerId, String follow, String stdOut, String stdErr, String since, String timestamps, String tail) {
        return this.client.getContainerLogsById(containerId, follow, stdOut, stdErr, since, timestamps, tail);
    }
}
