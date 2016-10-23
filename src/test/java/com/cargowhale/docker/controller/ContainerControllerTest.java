package com.cargowhale.docker.controller;

import com.cargowhale.docker.service.ContainerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerControllerTest {

    @InjectMocks
    private ContainerController controller;

    @Mock
    private ContainerService service;

    @Test
    public void getAllContainersReturnsEveryContainerFromService(){
        String expected = "ALL THE CATALOGS";
        when(this.service.getAllContainers()).thenReturn(expected);

        String actual = this.controller.getAllContainers();

        assertThat(actual, is(expected));
    }

    @Test
    public void getFilteredContainersReturnsFilteredContainersFromService(){
        String expected = "ALL RUNNING CATALOGS";
        String filter = "running";
        when(this.service.getFilteredContainers(filter)).thenReturn(expected);
        String actual = this.controller.getFilteredContainers(filter);
        verify(this.service).getFilteredContainers(filter);
        assertThat(actual, is(expected));
    }
}
