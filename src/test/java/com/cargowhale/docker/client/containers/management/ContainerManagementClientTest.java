package com.cargowhale.docker.client.containers.management;

import com.cargowhale.docker.client.DockerEndpointBuilder;
import com.cargowhale.docker.client.containers.management.state.ContainerChangeState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerManagementClientTest {

    private static final String DOCKER_ENDPOINT = "http://this.is.docker:yo";

    @InjectMocks
    private ContainerManagementClient client;

    @Mock
    private RestTemplate template;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void setContainerStatusSetsContainerToRunning() {
        String name = "testContainer";
        ContainerChangeState state = ContainerChangeState.RESTART;

        when(this.endpointBuilder.getContainerChangeStateEndpoint(name, state)).thenReturn(DOCKER_ENDPOINT);

        String actual = this.client.changeContainerState(name, state);

        verify(this.template).postForObject(DOCKER_ENDPOINT, null, String.class, state);
        assertThat(actual, is(name));
    }
}