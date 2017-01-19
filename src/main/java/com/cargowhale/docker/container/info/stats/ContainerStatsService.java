package com.cargowhale.docker.container.info.stats;

import com.spotify.docker.client.messages.ContainerStats;
import org.springframework.stereotype.Service;

@Service
public class ContainerStatsService {

    private final ContainerStatsClient client;
    private final ContainerStatsMapper mapper;

    public ContainerStatsService(final ContainerStatsClient client, final ContainerStatsMapper mapper) {
        this.client = client;
        this.mapper = mapper;
    }

    ContainerStatsResource getContainerStats(final String containerId) {
        ContainerStats containerStats = this.client.getContainerStats(containerId);
        return this.mapper.toResource(containerStats);
    }
}
