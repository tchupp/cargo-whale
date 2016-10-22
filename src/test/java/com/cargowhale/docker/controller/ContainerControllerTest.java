package com.cargowhale.docker.controller;

import com.cargowhale.docker.service.ContainerService;
import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.verify;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

/**
 * Created by nick on 21/10/16.
 */
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
        verify(this.service).getAllContainers();
        assertThat(actual, is(expected));
    }
}
