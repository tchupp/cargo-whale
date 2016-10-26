package com.cargowhale.docker.controller;

import com.cargowhale.docker.client.ContainerClient;
import com.cargowhale.docker.domain.ChangeStatusRequest;
import com.cargowhale.docker.domain.ChangeStatusResponse;
import com.cargowhale.docker.service.ContainerService;
import org.assertj.core.util.Maps;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerControllerTest {

    @InjectMocks
    private ContainerController controller;

    @Mock
    private ContainerClient client;

    @Mock
    private ContainerService service;

    @Test
    public void getAllContainersReturnsEveryContainerFromService() {
        String expected = "ALL THE CATALOGS";
        when(this.service.getAllContainers()).thenReturn(expected);

        String actual = this.controller.getAllContainers();

        assertThat(actual, is(expected));
    }

    @Test
    public void getFilteredContainersReturnsFilteredContainersFromService() {
        String expected = "ALL RUNNING CATALOGS";
        String filter = "running";

        when(this.service.getFilteredContainers(filter)).thenReturn(expected);

        String actual = this.controller.getFilteredContainers(filter);

        assertThat(actual, is(expected));
    }

    @Test
    public void setContainerStatusSetsContainerToRunning(){
        String status = "running";
        String name = "testName";
        ChangeStatusResponse expected = new ChangeStatusResponse().setName("returnName");

        ChangeStatusRequest statusRequest = new ChangeStatusRequest().setStatus(status);

        when(this.service.setContainerStatus(name, statusRequest)).thenReturn(expected);

        ChangeStatusResponse actual = this.controller.setContainerStatus(name, statusRequest);

        assertThat(actual, is(expected));
    }
}
