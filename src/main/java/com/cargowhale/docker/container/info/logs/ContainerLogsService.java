package com.cargowhale.docker.container.info.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ContainerLogsService {

    private final ContainerLogsClient client;

    @Autowired
    public ContainerLogsService(final ContainerLogsClient client) {
        this.client = client;
    }

    ContainerLogs getContainerLogsById(final String containerId, final LogFilters logFilters) {
        return this.client.getContainerLogs(containerId, logFilters);
    }
}
