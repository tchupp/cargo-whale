package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerStatsControllerTest {

    @InjectMocks
    private ContainerStatsController controller;

    @Mock
    private ContainerStatsService statsService;

    @Mock
    private ContainerStatsResourceAssembler statsResourceAssembler;

    @Test
    public void getContainerStatsById() {
        String containerId = "container id!";
        ContainerStats stats = new ContainerStats(containerId);
        ContainerStatsResource statsResource = mock(ContainerStatsResource.class);

        when(this.statsService.getContainerStats(containerId)).thenReturn(stats);
        when(this.statsResourceAssembler.toResource(stats)).thenReturn(statsResource);

        assertThat(this.controller.getContainerStats(containerId), is(statsResource));
    }
}