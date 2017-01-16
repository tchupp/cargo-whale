package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.ListContainersParam;
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

        List<Container> containers = Collections.singletonList(container1);
        List<ContainerResource> containerResources = Collections.singletonList(containerResource1);

        when(this.listContainersClient.listContainers()).thenReturn(containers);
        when(this.inspectContainerClient.inspectContainer(containerId1)).thenReturn(containerInfo1);
        when(this.mapper.toResource(container1, containerInfo1)).thenReturn(containerResource1);

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

        List<Container> containers = Arrays.asList(container1, container2);
        List<ContainerResource> containerResources = Arrays.asList(containerResource1, containerResource2);

        when(this.listContainersClient.listContainers(param)).thenReturn(containers);
        when(this.inspectContainerClient.inspectContainer(containerId1)).thenReturn(containerInfo1);
        when(this.inspectContainerClient.inspectContainer(containerId2)).thenReturn(containerInfo2);
        when(this.mapper.toResource(container1, containerInfo1)).thenReturn(containerResource1);
        when(this.mapper.toResource(container2, containerInfo2)).thenReturn(containerResource2);

        assertThat(this.service.getContainers(param), is(containerResources));
    }
}