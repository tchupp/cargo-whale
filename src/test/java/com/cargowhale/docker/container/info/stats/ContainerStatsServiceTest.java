package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
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

    @Test
    public void getContainerStatsById() {
        String containerId = "container_id";
        ContainerStats stats = new ContainerStats();

        when(this.client.getContainerStats(containerId)).thenReturn(stats);

        assertThat(this.service.getContainerStats(containerId), is(stats));
    }

}