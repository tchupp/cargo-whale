package com.cargowhale.docker.container.info.resource;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.container.info.resource.ListContainersParam.allContainers;
import static com.cargowhale.docker.container.info.resource.ListContainersParam.state;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerResourceControllerTest {

    @InjectMocks
    private ContainerResourceController controller;

    @Mock
    private ContainerResourceService service;

    @Mock
    private ContainerIndexResourceAssembler resourceAssembler;

    @Mock
    private ContainerResourceProcessor resourceProcessor;

    @Test
    public void listContainersReturnsContainerIndexResource() {
        ContainerIndexResource containerIndex = mock(ContainerIndexResource.class);
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.service.getContainers(allContainers())).thenReturn(containerResources);
        when(this.resourceAssembler.toResource(containerResources)).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(), is(containerIndex));
    }

    @Test
    public void listContainersReturnsContainerIndexResource_WithParams() {
        ContainerIndexResource containerIndex = mock(ContainerIndexResource.class);
        List<ContainerResource> containerResources = Collections.singletonList(mock(ContainerResource.class));

        when(this.service.getContainers(state(ContainerState.RUNNING), state(ContainerState.DEAD))).thenReturn(containerResources);
        when(this.resourceAssembler.toResource(containerResources)).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(new ContainerState[]{ContainerState.RUNNING, ContainerState.DEAD}), is(containerIndex));
    }

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = RandomStringUtils.random(10);
        ContainerResource containerResource = mock(ContainerResource.class);
        ContainerResource containerResourcePostProcess = mock(ContainerResource.class);

        when(this.service.getContainer(containerId)).thenReturn(containerResource);
        when(this.resourceProcessor.process(containerResource)).thenReturn(containerResourcePostProcess);

        assertThat(this.controller.inspectContainer(containerId), is(containerResourcePostProcess));
    }
}
