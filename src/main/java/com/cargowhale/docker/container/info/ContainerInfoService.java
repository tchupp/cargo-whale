package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.inspect.ContainerDetails;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.container.info.index.ContainerIndex;
import com.cargowhale.docker.container.info.index.ContainerIndexBuilder;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class ContainerInfoService {

    private final ContainerInfoClient client;
    private final ContainerIndexBuilder containerIndexBuilder;

    @Autowired
    public ContainerInfoService(final ContainerInfoClient client, final ContainerIndexBuilder containerIndexBuilder) {
        this.client = client;
        this.containerIndexBuilder = containerIndexBuilder;
    }

    public ContainerIndex getAllContainers() {
        List<ContainerListItem> containerList = this.client.listContainers();
        return this.containerIndexBuilder.buildContainerIndex(containerList);
    }

    public ContainerIndex getContainersFilterByStatus(final Set<ContainerState> containerStates) {
        ListContainerFilters listContainerFilters = new ListContainerFilters(containerStates);

        List<ContainerListItem> containerList = this.client.listContainers(listContainerFilters);
        return this.containerIndexBuilder.buildContainerIndex(containerList);
    }

    ContainerDetails getContainerDetailsById(final String containerId) {
        return this.client.inspectContainer(containerId);
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogs(containerId, logFilters);
    }

    ContainerStats getContainerStatsById(final String containerId){
        return this.client.getContainerStats(containerId);
    }
}
