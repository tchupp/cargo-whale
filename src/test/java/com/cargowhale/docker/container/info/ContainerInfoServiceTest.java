package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.client.LogFilters;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.top.ContainerTopResponse;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.container.info.top.ContainerProcessIndex;
import com.cargowhale.docker.container.info.top.ContainerProcessIndexBuilder;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

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

    @Mock
    private ContainerIndexBuilder containerIndexBuilder;

    @Mock
    private ContainerProcessIndexBuilder processIndexBuilder;

    @Test
    public void getAllContainersReturnsAllContainersFromClient() {
        List<ContainerSummary> containerSummaryList = Collections.singletonList(mock(ContainerSummary.class));
        ContainerIndex containerIndex = mock(ContainerIndex.class);

        when(this.client.getAllContainers()).thenReturn(containerSummaryList);
        when(this.containerIndexBuilder.buildContainerIndex(containerSummaryList)).thenReturn(containerIndex);

        assertThat(this.service.getAllContainers(), is(containerIndex));
    }

    @Test
    public void getFilteredContainersReturnsFilteredContainers() {
        ContainerState[] containerStatuses = Arrays.array(ContainerState.DEAD, ContainerState.PAUSED);
        StateFilters stateFilters = new StateFilters(containerStatuses);
        DockerContainerFilters filters = new DockerContainerFilters(containerStatuses);

        List<ContainerSummary> containerSummaryList = Collections.singletonList(mock(ContainerSummary.class));
        ContainerIndex containerIndex = mock(ContainerIndex.class);

        when(this.client.getFilteredContainers(filters)).thenReturn(containerSummaryList);
        when(this.containerIndexBuilder.buildContainerIndex(containerSummaryList)).thenReturn(containerIndex);

        assertThat(this.service.getContainersFilterByStatus(stateFilters), is(containerIndex));
    }

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = "container_id";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.client.getContainerDetailsById(containerId)).thenReturn(containerDetails);

        assertThat(this.service.getContainerDetailsById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsById() throws Exception {
        String containerId = "container_id";
        LogFilters filters = new LogFilters();

        ContainerLogs containerLogs = mock(ContainerLogs.class);

        when(this.client.getContainerLogsById(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.service.getContainerLogsById(containerId, filters), is(containerLogs));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndex() {
        String containerId = "container_id";
        ContainerTopResponse response = mock(ContainerTopResponse.class);
        ContainerProcessIndex processIndex = mock(ContainerProcessIndex.class);

        when(this.client.getContainerProcessesById(containerId)).thenReturn(response);
        when(this.processIndexBuilder.buildProcessIndex(containerId, response)).thenReturn(processIndex);

        assertThat(this.service.getContainerProcessesById(containerId), is(processIndex));
    }
}