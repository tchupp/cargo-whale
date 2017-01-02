package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.inspect.ContainerDetails;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.stats.ContainerStats;
import com.cargowhale.docker.container.info.index.ContainerIndex;
import com.cargowhale.docker.container.info.index.ContainerIndexBuilder;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.assertj.core.util.Sets.newLinkedHashSet;
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
        Set<ContainerState> containerStates = newLinkedHashSet(ContainerState.DEAD, ContainerState.PAUSED);
        ListContainerFilters filters = new ListContainerFilters(containerStates);

        List<ContainerListItem> containerList = Collections.singletonList(mock(ContainerListItem.class));
        ContainerIndex containerIndex = mock(ContainerIndex.class);

        when(this.client.listContainers(filters)).thenReturn(containerList);
        when(this.containerIndexBuilder.buildContainerIndex(containerList)).thenReturn(containerIndex);

        assertThat(this.service.getContainersFilterByStatus(containerStates), is(containerIndex));
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
    public void getContainerStatsById() {
        String containerId = "container_id";
        ContainerStats stats = new ContainerStats();

        when(this.client.getContainerStats(containerId)).thenReturn(stats);

        assertThat(this.service.getContainerStatsById(containerId), is(stats));
    }
}