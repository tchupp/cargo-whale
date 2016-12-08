package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerLogs;
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
        List<ContainerListItem> containerList = this.client.listContainers();
        return this.containerIndexBuilder.buildContainerIndex(containerList);
    }

    ContainerIndex getContainersFilterByStatus(final StateFilters stateFilters) {
        ListContainerFilters listContainerFilters = new ListContainerFilters(stateFilters.getState());

        List<ContainerListItem> containerList = this.client.listContainers(listContainerFilters);
        return this.containerIndexBuilder.buildContainerIndex(containerList);
    }

    ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.inspectContainer(containerId);
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogs(containerId, logFilters);
    }

    ContainerProcessIndex getContainerProcessesById(final String containerId) {
        ContainerTop dockerIndex = this.client.getContainerProcesses(containerId);
        return this.processIndexBuilder.buildProcessIndex(containerId, dockerIndex);
    }
}
