package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.client.containers.ListContainersParam.state;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexServiceTest {

    @InjectMocks
    private ContainerIndexService service;

    @Mock
    private ListContainersClient client;

    @Mock
    private ContainerMapper mapper;

    @Test
    public void getContainerIndex_NoParams_ReturnsContainerIndex() throws Exception {
        List<Container> containers = Collections.singletonList(mock(Container.class));
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.client.listContainers()).thenReturn(containers);
        when(this.mapper.toResources(containers)).thenReturn(containerResources);

        assertThat(this.service.getContainerIndex(), is(containerResources));
    }

    @Test
    public void getContainerIndex_WithParams_ReturnsContainerIndex() throws Exception {
        List<Container> containers = Collections.singletonList(mock(Container.class));
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.client.listContainers(state(ContainerState.EXITED))).thenReturn(containers);
        when(this.mapper.toResources(containers)).thenReturn(containerResources);

        assertThat(this.service.getContainerIndex(state(ContainerState.EXITED)), is(containerResources));
    }
}