package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;
import static com.cargowhale.docker.client.containers.ListContainersParam.state;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexControllerTest {

    @InjectMocks
    private ContainerIndexController controller;

    @Mock
    private ContainerIndexService service;

    @Mock
    private ContainerIndexResourceAssembler resourceAssembler;

    @Test
    public void listContainersReturnsContainerIndexResource() {
        ContainerIndexResource containerIndex = new ContainerIndexResource();
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.service.getContainerIndex(allContainers())).thenReturn(containerResources);
        when(this.resourceAssembler.toResource(containerResources)).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(), is(containerIndex));
    }

    @Test
    public void listContainersReturnsContainerIndexResource_WithParams() {
        ContainerIndexResource containerIndex = new ContainerIndexResource();
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.service.getContainerIndex(state(ContainerState.RUNNING), state(ContainerState.DEAD))).thenReturn(containerResources);
        when(this.resourceAssembler.toResource(containerResources)).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(new ContainerState[]{ContainerState.RUNNING, ContainerState.DEAD}), is(containerIndex));
    }
}
