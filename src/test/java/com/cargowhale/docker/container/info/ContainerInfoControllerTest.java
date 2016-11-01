package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerInfoCollectionVM;
import com.cargowhale.docker.container.StateFilters;
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
public class ContainerInfoControllerTest {

    @InjectMocks
    private ContainerInfoController controller;

    @Mock
    private ContainerInfoService service;

    @Test
    public void getAllContainersReturnsEveryContainerFromService() {
        ContainerInfoCollectionVM containerInfoCollectionVM = mock(ContainerInfoCollectionVM.class);

        when(this.service.getAllContainers()).thenReturn(containerInfoCollectionVM);

        assertThat(this.controller.getAllContainers(), is(containerInfoCollectionVM));
    }

    @Test
    public void getContainersFilterByStatusReturnsFilteredContainersFromService() {
        ContainerInfoCollectionVM containerInfoCollectionVM = mock(ContainerInfoCollectionVM.class);
        StateFilters stateFilters = mock(StateFilters.class);

        when(this.service.getContainersFilterByStatus(stateFilters)).thenReturn(containerInfoCollectionVM);

        assertThat(this.controller.getContainersFilterByStatus(stateFilters), is(containerInfoCollectionVM));
    }
}
