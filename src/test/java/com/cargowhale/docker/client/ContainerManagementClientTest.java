package com.cargowhale.docker.client;

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
        String status = "start";

        when(this.endpointBuilder.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);

        String actual = this.client.setContainerStatus(name, status);

        verify(this.template).postForObject(DOCKER_ENDPOINT + "/{name}/{status}", null, String.class, name, status);
        assertThat(actual, is(name));
    }

}