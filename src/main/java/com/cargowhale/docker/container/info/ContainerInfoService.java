package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.client.info.ContainerInfoClient;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    ContainerProcessIndex getContainerProcessesById(final String id) {
        DockerContainerProcessIndex dockerIndex = this.client.getContainerProcessesById(id);

        List<ContainerProcess> processes = new ArrayList<>();
        for (final List<String> dockerProcess : dockerIndex.getProcesses()) {
            ContainerProcess process = new ContainerProcess(
                    dockerProcess.get(0),
                    dockerProcess.get(1),
                    dockerProcess.get(2),
                    dockerProcess.get(3),
                    dockerProcess.get(4),
                    dockerProcess.get(5),
                    dockerProcess.get(6),
                    dockerProcess.get(7));
            processes.add(process);
        }

        return new ContainerProcessIndex(id, processes);
    }
}
