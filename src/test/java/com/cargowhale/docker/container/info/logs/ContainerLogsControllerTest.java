package com.cargowhale.docker.container.info.logs;

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
public class ContainerLogsControllerTest {

    @InjectMocks
    private ContainerLogsController controller;

    @Mock
    private ContainerLogsService logsService;

    @Mock
    private ContainerLogsResourceAssembler logsResourceAssembler;

    @Test
    public void getContainerLogsById() {
        String containerId = "container id!";
        LogFilters filters = new LogFilters();

        ContainerLogs containerLogs = mock(ContainerLogs.class);
        ContainerLogsResource containerLogsResource = mock(ContainerLogsResource.class);

        when(this.logsService.getContainerLogsById(containerId, filters)).thenReturn(containerLogs);
        when(this.logsResourceAssembler.toResource(containerLogs)).thenReturn(containerLogsResource);

        assertThat(this.controller.getContainerLogsById(containerId, filters), is(containerLogsResource));
    }
}