package com.cargowhale.docker.client.containers.management;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.containers.management.state.ContainerChangeState;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerManagementClientTest {

    @InjectMocks
    private ContainerManagementClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void setContainerStatusSetsContainerToRunning() {
        String containerStateEndpoint = "/change/state!";
        String name = "testContainer";
        ContainerChangeState state = ContainerChangeState.RESTART;

        when(this.endpointBuilder.getContainerChangeStateEndpoint(name, state)).thenReturn(containerStateEndpoint);

        String actual = this.client.changeContainerState(name, state);

        verify(this.restTemplate).postForObject(containerStateEndpoint + "?t=5", null, String.class, state);
        assertThat(actual, is(name));
    }
}