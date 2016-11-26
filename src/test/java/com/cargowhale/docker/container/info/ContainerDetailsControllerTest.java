package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.info.model.ContainerDetails;
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

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = "container id!";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.service.getContainerDetailsById(containerId)).thenReturn(containerDetails);

        assertThat(this.controller.getContainerById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsById() {
        String containerId = "container id!";
        String follow = "0";
        String stdOut = "0";
        String stdErr = "0";
        String since = "0";
        String timestamps = "0";
        String tail = "0";
        String containerLogs = "logs";

        when(this.service.getContainerLogsById(containerId, follow, stdOut, stdErr, since, timestamps, tail)).thenReturn(containerLogs);

        assertThat(this.controller.getContainerLogsById(containerId, follow, stdOut, stdErr, since, timestamps, tail), is(containerLogs));
    }
}