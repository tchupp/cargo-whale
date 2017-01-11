package com.cargowhale.docker.client.containers.info;

import com.cargowhale.docker.client.containers.info.inspect.ContainerDetails;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerInfoClientTest {

    @InjectMocks
    private ContainerInfoClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void inspectContainerReturnsCorrectContainer() throws Exception {
        String inspectContainerEndpoint = UUID.randomUUID().toString();
        String containerId = "container_id_yo";

        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.endpointBuilder.getInspectContainerEndpoint(containerId)).thenReturn(inspectContainerEndpoint);
        when(this.restTemplate.getForObject(inspectContainerEndpoint, ContainerDetails.class)).thenReturn(containerDetails);

        assertThat(this.client.inspectContainer(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsReturnsCorrectContainerLogs() throws Exception {
        String containerLogsEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        LogFilters filters = new LogFilters(true, false, true, true, true, 0, "265");
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(containerLogsEndpoint).queryParams(filters.asQueryParameters());

        String logs = "These are some fancy logs!";

        when(this.endpointBuilder.getContainerLogsEndpoint(containerId)).thenReturn(containerLogsEndpoint);
        when(this.restTemplate.getForObject(builder.toUriString(), String.class)).thenReturn(logs);

        ContainerLogs containerLogs = this.client.getContainerLogs(containerId, filters);
        assertThat(containerLogs.getLogs(), is(logs));
    }

    @Test
    public void getContainerProcessesReturnsCorrectContainerProcesses() {
        String containerTopEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        ContainerTop containerTop = Mockito.mock(ContainerTop.class);

        when(this.endpointBuilder.getContainerProcessesEndpoint(containerId)).thenReturn(containerTopEndpoint);
        when(this.restTemplate.getForObject(containerTopEndpoint, ContainerTop.class)).thenReturn(containerTop);

        assertThat(this.client.getContainerProcesses(containerId), is(containerTop));
    }

    @Test
    public void getContainerStatsReturnsCorrectContainerStats() {
        String containerStatsEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        ContainerStats containerStats = Mockito.mock(ContainerStats.class);

        when(this.endpointBuilder.getContainerStatsEndpoint(containerId)).thenReturn(containerStatsEndpoint);
        when(this.restTemplate.getForObject(containerStatsEndpoint, ContainerStats.class)).thenReturn(containerStats);

        assertThat(this.client.getContainerStats(containerId), is(containerStats));
    }
}

