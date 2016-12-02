package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.LogFilters;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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

    public ContainerProcessIndex getContainerProcessesById(String id) {
        DockerContainerProcessIndex dockerIndex = this.client.getContainerProcessesById(id);

        List<ContainerProcess> processes = new ArrayList<>();
        for (List<String> dockerProcess : dockerIndex.getProcesses()) {
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
