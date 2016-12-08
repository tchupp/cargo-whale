package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerLogs;
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
        List<ContainerListItem> containerList = Collections.singletonList(mock(ContainerListItem.class));
        ContainerIndex containerIndex = mock(ContainerIndex.class);

        when(this.client.listContainers()).thenReturn(containerList);
        when(this.containerIndexBuilder.buildContainerIndex(containerList)).thenReturn(containerIndex);

        assertThat(this.service.getAllContainers(), is(containerIndex));
    }

    @Test
    public void getFilteredContainersReturnsFilteredContainers() {
        ContainerState[] containerStatuses = Arrays.array(ContainerState.DEAD, ContainerState.PAUSED);
        StateFilters stateFilters = new StateFilters(containerStatuses);
        ListContainerFilters filters = new ListContainerFilters(containerStatuses);

        List<ContainerListItem> containerList = Collections.singletonList(mock(ContainerListItem.class));
        ContainerIndex containerIndex = mock(ContainerIndex.class);

        when(this.client.listContainers(filters)).thenReturn(containerList);
        when(this.containerIndexBuilder.buildContainerIndex(containerList)).thenReturn(containerIndex);

        assertThat(this.service.getContainersFilterByStatus(stateFilters), is(containerIndex));
    }

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = "container_id";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.client.inspectContainer(containerId)).thenReturn(containerDetails);

        assertThat(this.service.getContainerDetailsById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsById() throws Exception {
        String containerId = "container_id";
        LogFilters filters = new LogFilters();

        ContainerLogs containerLogs = mock(ContainerLogs.class);

        when(this.client.getContainerLogs(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.service.getContainerLogsById(containerId, filters), is(containerLogs));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndex() {
        String containerId = "container_id";
        ContainerTop response = mock(ContainerTop.class);
        ContainerProcessIndex processIndex = mock(ContainerProcessIndex.class);

        when(this.client.getContainerProcesses(containerId)).thenReturn(response);
        when(this.processIndexBuilder.buildProcessIndex(containerId, response)).thenReturn(processIndex);

        assertThat(this.service.getContainerProcessesById(containerId), is(processIndex));
    }
}