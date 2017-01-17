package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.container.info.model.ContainerLogs;
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
public class ContainerInfoServiceTest {

    @InjectMocks
    private ContainerInfoService service;

    @Mock
    private ContainerInfoClient client;

    @Test
    public void getContainerLogsById() throws Exception {
        String containerId = "container_id";
        LogFilters filters = new LogFilters();

        ContainerLogs containerLogs = mock(ContainerLogs.class);

        when(this.client.getContainerLogs(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.service.getContainerLogsById(containerId, filters), is(containerLogs));
    }

    @Test
    public void getContainerStatsById() {
        String containerId = "container_id";
        ContainerStats stats = new ContainerStats();

        when(this.client.getContainerStats(containerId)).thenReturn(stats);

        assertThat(this.service.getContainerStatsById(containerId), is(stats));
    }
}