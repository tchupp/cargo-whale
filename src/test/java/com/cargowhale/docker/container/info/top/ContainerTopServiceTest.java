package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.containers.info.ContainerInfoClient;
import com.cargowhale.docker.client.core.exception.BadContainerStateException;
import com.cargowhale.docker.container.info.details.InspectContainerClient;
import com.spotify.docker.client.messages.ContainerInfo;
import com.spotify.docker.client.messages.ContainerState;
import com.spotify.docker.client.messages.TopResults;
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
    private ContainerInfoClient infoClient;

    @Mock
    private InspectContainerClient inspectContainerClient;

    @Mock
    private ContainerProcessIndexBuilder processIndexBuilder;

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndex() {
        String containerId = "container_id";
        ContainerInfo containerInfo = mock(ContainerInfo.class);
        ContainerState containerState = mock(ContainerState.class);

        when(containerInfo.state()).thenReturn(containerState);
        when(containerState.running()).thenReturn(true);

        TopResults results = mock(TopResults.class);
        ContainerProcessIndex processIndex = new ContainerProcessIndex(containerId, newArrayList());

        when(this.inspectContainerClient.inspectContainer(containerId)).thenReturn(containerInfo);

        when(this.infoClient.getContainerProcesses(containerId)).thenReturn(results);
        when(this.processIndexBuilder.buildProcessIndex(containerId, results)).thenReturn(processIndex);

        assertThat(this.service.getContainerProcessesById(containerId), is(processIndex));
    }

    @Test
    public void throwsContainerNotRunningException_IfContainerIsNotRunning() throws Exception {
        this.thrown.expect(BadContainerStateException.class);
        this.thrown.expectMessage("Container in exited state");

        String containerId = "container_id";
        ContainerInfo containerInfo = mock(ContainerInfo.class);
        ContainerState containerState = mock(ContainerState.class);

        when(containerInfo.state()).thenReturn(containerState);
        when(containerState.running()).thenReturn(false);
        when(containerState.status()).thenReturn("exited");

        when(this.inspectContainerClient.inspectContainer(containerId)).thenReturn(containerInfo);

        this.service.getContainerProcessesById(containerId);
    }
}