package com.cargowhale.docker.container.management;

import com.cargowhale.docker.domain.ChangeStateRequest;
import com.cargowhale.docker.domain.ChangeStateResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerManagementControllerTest {

    @InjectMocks
    private ContainerManagementController controller;

    @Mock
    private ContainerManagementService service;

    @Test
    public void changeContainerStateCallsService() {
        String state = "running";
        String name = "testName";
        ChangeStateResponse expected = new ChangeStateResponse("returnName");

        ChangeStateRequest stateRequest = new ChangeStateRequest(state);

        when(this.service.changeContainerState(name, stateRequest)).thenReturn(expected);

        assertThat(this.controller.changeContainerState(name, stateRequest), is(expected));
    }
}