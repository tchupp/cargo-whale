package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ListContainersParam;
import com.cargowhale.docker.container.info.ContainerState;
import com.cargowhale.docker.container.info.details.InspectContainerClient;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerInfo;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;
import static com.cargowhale.docker.client.containers.ListContainersParam.state;
import static com.cargowhale.docker.test.ContainerTestUtilities.buildContainerWithId;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexServiceTest {

    @InjectMocks
    private ContainerIndexService service;

    @Mock
    private ListContainersClient listContainersClient;

    @Mock
    private InspectContainerClient inspectContainerClient;

    @Mock
    private ContainerMapper mapper;

    @Mock
    private ContainerResourceProcessor resourceProcessor;

    @Test
    public void getContainersReturnsContainerResources_None() throws Exception {
        ListContainersParam param = state(ContainerState.EXITED);

        List<Container> containers = Collections.emptyList();
        List<ContainerResource> containerResources = Collections.emptyList();

        when(this.listContainersClient.listContainers(param)).thenReturn(containers);

        assertThat(this.service.getContainers(param), is(containerResources));
    }

    @Test
    public void getContainersReturnsContainerResources_One() throws Exception {
        String containerId1 = RandomStringUtils.random(10);
        Container container1 = buildContainerWithId(containerId1);
        ContainerInfo containerInfo1 = mock(ContainerInfo.class);
        ContainerResource containerResource1 = mock(ContainerResource.class);
        ContainerResource containerResourcePostProcessed1 = mock(ContainerResource.class);

        List<Container> containers = Collections.singletonList(container1);
        List<ContainerResource> containerResources = Collections.singletonList(containerResourcePostProcessed1);

        when(this.listContainersClient.listContainers()).thenReturn(containers);
        when(this.inspectContainerClient.inspectContainer(containerId1)).thenReturn(containerInfo1);
        when(this.mapper.toResource(container1, containerInfo1)).thenReturn(containerResource1);
        when(this.resourceProcessor.process(containerResource1)).thenReturn(containerResourcePostProcessed1);

        assertThat(this.service.getContainers(), is(containerResources));

    }

    @Test
    public void getContainersReturnsContainerResources_Many() throws Exception {
        ListContainersParam param = state(ContainerState.CREATED);

        String containerId1 = RandomStringUtils.random(10);
        String containerId2 = RandomStringUtils.random(10);
        Container container1 = buildContainerWithId(containerId1);
        Container container2 = buildContainerWithId(containerId2);
        ContainerInfo containerInfo1 = mock(ContainerInfo.class);
        ContainerInfo containerInfo2 = mock(ContainerInfo.class);
        ContainerResource containerResource1 = mock(ContainerResource.class);
        ContainerResource containerResource2 = mock(ContainerResource.class);
        ContainerResource containerResourcePostProcessed1 = mock(ContainerResource.class);
        ContainerResource containerResourcePostProcessed2 = mock(ContainerResource.class);

        List<Container> containers = Arrays.asList(container1, container2);
        List<ContainerResource> containerResources = Arrays.asList(containerResourcePostProcessed1, containerResourcePostProcessed2);

        when(this.listContainersClient.listContainers(param)).thenReturn(containers);
        when(this.inspectContainerClient.inspectContainer(containerId1)).thenReturn(containerInfo1);
        when(this.inspectContainerClient.inspectContainer(containerId2)).thenReturn(containerInfo2);
        when(this.mapper.toResource(container1, containerInfo1)).thenReturn(containerResource1);
        when(this.mapper.toResource(container2, containerInfo2)).thenReturn(containerResource2);
        when(this.resourceProcessor.process(containerResource1)).thenReturn(containerResourcePostProcessed1);
        when(this.resourceProcessor.process(containerResource2)).thenReturn(containerResourcePostProcessed2);

        assertThat(this.service.getContainers(param), is(containerResources));
    }

    @Test
    public void getContainerReturnsContainerResource() throws Exception {
        String containerId_Right = RandomStringUtils.random(10);
        String containerId_Wrong = RandomStringUtils.random(10);
        Container container_Right = buildContainerWithId(containerId_Right);
        Container container_Wrong = buildContainerWithId(containerId_Wrong);
        ContainerInfo containerInfo = mock(ContainerInfo.class);
        ContainerResource containerResource = mock(ContainerResource.class);
        ContainerResource containerResourcePostProcessed = mock(ContainerResource.class);

        List<Container> containers = Arrays.asList(container_Right, container_Wrong);

        when(this.listContainersClient.listContainers(allContainers())).thenReturn(containers);
        when(this.inspectContainerClient.inspectContainer(containerId_Right)).thenReturn(containerInfo);
        when(this.mapper.toResource(container_Right, containerInfo)).thenReturn(containerResource);
        when(this.resourceProcessor.process(containerResource)).thenReturn(containerResourcePostProcessed);

        assertThat(this.service.getContainer(containerId_Right), is(containerResourcePostProcessed));
    }
}