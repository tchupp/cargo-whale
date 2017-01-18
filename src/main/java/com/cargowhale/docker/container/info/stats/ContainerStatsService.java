package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import org.springframework.stereotype.Service;

@Service
public class ContainerStatsService {

    private final ContainerStatsClient client;

    public ContainerStatsService(final ContainerStatsClient client) {
        this.client = client;
    }

    ContainerStats getContainerStats(final String containerId) {
        return this.client.getContainerStats(containerId);
    }
}
