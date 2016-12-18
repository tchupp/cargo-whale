package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.container.info.ContainerInfoService;
import com.cargowhale.docker.container.info.index.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Set;

import static org.assertj.core.util.Sets.newLinkedHashSet;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexControllerTest {

    @InjectMocks
    private ContainerIndexController controller;

    @Mock
    private ContainerInfoService service;

    @Mock
    private ContainerIndexResourceAssembler resourceAssembler;

    @Test
    public void getAllContainersReturnsEveryContainerFromService() {
        ContainerIndex containerIndex = mock(ContainerIndex.class);
        ContainerIndexResource containerListResponseItemResource = mock(ContainerIndexResource.class);

        when(this.service.getAllContainers()).thenReturn(containerIndex);
        when(this.resourceAssembler.toResource(containerIndex)).thenReturn(containerListResponseItemResource);

        assertThat(this.controller.listContainers(), is(containerListResponseItemResource));
    }

    @Test
    public void getContainersFilterByStatusReturnsFilteredContainersFromService() {
        ContainerIndex containerIndex = mock(ContainerIndex.class);
        ContainerIndexResource containerListResponseItemResource = mock(ContainerIndexResource.class);
        Set<ContainerState> states = newLinkedHashSet();
        StateFilters stateFilters = new StateFilters(states);

        when(this.service.getContainersFilterByStatus(states)).thenReturn(containerIndex);
        when(this.resourceAssembler.toResource(containerIndex)).thenReturn(containerListResponseItemResource);

        assertThat(this.controller.listContainers(stateFilters), is(containerListResponseItemResource));
    }
}
