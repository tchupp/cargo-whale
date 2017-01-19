package com.cargowhale.docker.container.info.stats;

import com.spotify.docker.client.messages.ContainerStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerStatsServiceTest {

    @InjectMocks
    private ContainerStatsService service;

    @Mock
    private ContainerStatsClient client;

    @Mock
    private ContainerStatsMapper mapper;

    @Test
    public void getContainerStatsById() {
        String containerId = "container_id";
        ContainerStats containerStats = new ContainerStats();
        ContainerStatsResource containerStatsResource = new ContainerStatsResource();

        when(this.client.getContainerStats(containerId)).thenReturn(containerStats);
        when(this.mapper.toResource(containerStats)).thenReturn(containerStatsResource);

        assertThat(this.service.getContainerStats(containerId), is(containerStatsResource));
    }

}