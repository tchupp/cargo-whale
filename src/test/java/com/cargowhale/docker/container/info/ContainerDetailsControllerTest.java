package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.resource.*;
import com.cargowhale.docker.container.info.top.ContainerProcessIndex;
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
public class ContainerDetailsControllerTest {

    @InjectMocks
    private ContainerDetailsController controller;

    @Mock
    private ContainerInfoService service;

    @Mock
    private ContainerDetailsResourceAssembler detailsResourceAssembler;

    @Mock
    private ContainerLogsResourceAssembler logsResourceAssembler;

    @Mock
    private ContainerProcessesResourceAssembler processesResourceAssembler;

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = "container id!";
        ContainerDetails containerDetails = mock(ContainerDetails.class);
        ContainerDetailsResource containerDetailsResource = mock(ContainerDetailsResource.class);

        when(this.service.getContainerDetailsById(containerId)).thenReturn(containerDetails);
        when(this.detailsResourceAssembler.toResource(containerDetails)).thenReturn(containerDetailsResource);

        assertThat(this.controller.getContainerById(containerId), is(containerDetailsResource));
    }

    @Test
    public void getContainerLogsById() {
        String containerId = "container id!";
        LogFilters filters = new LogFilters();

        ContainerLogs containerLogs = mock(ContainerLogs.class);
        ContainerLogsResource containerLogsResource = mock(ContainerLogsResource.class);

        when(this.service.getContainerLogsById(containerId, filters)).thenReturn(containerLogs);
        when(this.logsResourceAssembler.toResource(containerLogs)).thenReturn(containerLogsResource);

        assertThat(this.controller.getContainerLogsById(containerId, filters), is(containerLogsResource));
    }

    @Test
    public void getContainerProcessesById() {
        String containerId = "container id!";
        ContainerProcessIndex processes = mock(ContainerProcessIndex.class);
        ContainerProcessesResource processesResource = mock(ContainerProcessesResource.class);

        when(this.service.getContainerProcessesById(containerId)).thenReturn(processes);
        when(this.processesResourceAssembler.toResource(processes)).thenReturn(processesResource);

        assertThat(this.controller.getContainerProcessesById(containerId), is(processesResource));
    }
}