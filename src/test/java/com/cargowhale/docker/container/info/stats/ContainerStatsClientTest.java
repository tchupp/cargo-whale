package com.cargowhale.docker.container.info.stats;

import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import org.apache.commons.lang.RandomStringUtils;
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
public class ContainerStatsClientTest {

    @InjectMocks
    private ContainerStatsClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void getContainerStats() throws Exception {
        String containerId = RandomStringUtils.random(10);
        String containerStatsEndpoint = "/api/container/logs";
        ContainerStats containerStats = mock(ContainerStats.class);

        when(this.endpointBuilder.getContainerStatsEndpoint(containerId)).thenReturn(containerStatsEndpoint);
        when(this.restTemplate.getForObject(containerStatsEndpoint, ContainerStats.class)).thenReturn(containerStats);

        assertThat(this.client.getContainerStats(containerId), is(containerStats));
    }
}