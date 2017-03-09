package com.cargowhale.docker.container.info.logs;

import com.spotify.docker.client.LogStream;
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
    private ContainerLogsClient logsClient;

    @Test
    public void getStdOutLogs_ReturnsLogsFromServiceWithStdOutTrue() {
        String containerId = "container id!";
        LogFilters filters = new LogFilters();
        filters.setStdout(true);

        LogStream containerLogs = mock(LogStream.class);

        when(this.logsClient.getContainerLogStream(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.controller.getStdOutLogs(containerId, true), is(containerLogs));
    }

    @Test
    public void getStdErrLogs_ReturnsLogsFromServiceWithStdErrTrue() {
        String containerId = "container id!";
        LogFilters filters = new LogFilters();
        filters.setStderr(true);

        LogStream containerLogs = mock(LogStream.class);

        when(this.logsClient.getContainerLogStream(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.controller.getStdErrLogs(containerId, true), is(containerLogs));
    }

    @Test
    public void getAllLogs_ReturnsLogsFromServiceWithBothStdOutAndStdErrTrue() {
        String containerId = "container id!";
        LogFilters filters = new LogFilters();
        filters.setStdout(true);
        filters.setStderr(true);

        LogStream containerLogs = mock(LogStream.class);

        when(this.logsClient.getContainerLogStream(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.controller.getAllLogs(containerId), is(containerLogs));
    }
}