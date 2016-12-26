package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.containers.info.inspect.ContainerDetails;
import com.cargowhale.docker.client.containers.info.inspect.ContainerDetailsState;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.client.core.exception.BadContainerStateException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerTopServiceTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private ContainerTopService service;

    @Mock
    private ContainerInfoClient client;

    @Mock
    private ContainerProcessIndexBuilder processIndexBuilder;

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndex() {
        String containerId = "container_id";
        ContainerDetailsState containerDetailsState = new ContainerDetailsState(ContainerState.RUNNING);
        ContainerDetails containerDetails = new ContainerDetails(containerDetailsState);

        ContainerTop response = mock(ContainerTop.class);
        ContainerProcessIndex processIndex = new ContainerProcessIndex(containerId, newArrayList());

        when(this.client.inspectContainer(containerId)).thenReturn(containerDetails);

        when(this.client.getContainerProcesses(containerId)).thenReturn(response);
        when(this.processIndexBuilder.buildProcessIndex(containerId, response)).thenReturn(processIndex);

        assertThat(this.service.getContainerProcessesById(containerId), is(processIndex));
    }

    @Test
    public void throwsContainerNotRunningException_IfContainerIsNotRunning() throws Exception {
        this.thrown.expect(BadContainerStateException.class);
        this.thrown.expectMessage("Container in exited state");

        String containerId = "container_id";
        ContainerDetailsState containerDetailsState = new ContainerDetailsState(ContainerState.EXITED);
        ContainerDetails containerDetails = new ContainerDetails(containerDetailsState);

        when(this.client.inspectContainer(containerId)).thenReturn(containerDetails);

        this.service.getContainerProcessesById(containerId);
    }
}