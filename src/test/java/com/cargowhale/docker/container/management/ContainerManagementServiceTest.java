package com.cargowhale.docker.container.management;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerManagementServiceTest {

    @InjectMocks
    private ContainerManagementService service;

    @Mock
    private ContainerManagementClient client;

    @Test
    public void setContainerStateReturnsContainerNameFromClient() {
        String name = "stoppedContainer";
        ContainerChangeState state = ContainerChangeState.START;
        String expectedName = "runningContainer";

        ChangeStateRequest request = new ChangeStateRequest(state);

        when(this.client.changeContainerState(name, state)).thenReturn(expectedName);

        ChangeStateResponse actual = this.service.changeContainerState(name, request);
        assertThat(actual.getName(), is(expectedName));
    }
}