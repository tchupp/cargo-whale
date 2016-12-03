package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.resource.ContainerIndexResource;
import com.cargowhale.docker.container.info.resource.ContainerIndexResourceAssembler;
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
public class ContainerSummaryControllerTest {

    @InjectMocks
    private ContainerSummaryController controller;

    @Mock
    private ContainerInfoService service;

    @Mock
    private ContainerIndexResourceAssembler resourceAssembler;

    @Test
    public void getAllContainersReturnsEveryContainerFromService() {
        ContainerIndex containerIndex = mock(ContainerIndex.class);
        ContainerIndexResource containerIndexResource = mock(ContainerIndexResource.class);

        when(this.service.getAllContainers()).thenReturn(containerIndex);
        when(this.resourceAssembler.toResource(containerIndex)).thenReturn(containerIndexResource);

        assertThat(this.controller.getAllContainers(), is(containerIndexResource));
    }

    @Test
    public void getContainersFilterByStatusReturnsFilteredContainersFromService() {
        ContainerIndex containerIndex = mock(ContainerIndex.class);
        ContainerIndexResource containerIndexResource = mock(ContainerIndexResource.class);
        StateFilters stateFilters = mock(StateFilters.class);

        when(this.service.getContainersFilterByStatus(stateFilters)).thenReturn(containerIndex);
        when(this.resourceAssembler.toResource(containerIndex)).thenReturn(containerIndexResource);

        assertThat(this.controller.getContainersFilterByStatus(stateFilters), is(containerIndexResource));
    }
}
