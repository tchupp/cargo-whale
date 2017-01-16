package com.cargowhale.docker.container.info.details;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.ContainerInfo;
import org.apache.commons.lang.RandomStringUtils;
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
public class InspectContainerClientTest {

    @InjectMocks
    private InspectContainerClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void inspectContainer() throws Exception {
        String containerId = "this_container";
        String inspectContainerEndpoint = RandomStringUtils.random(10);
        ContainerInfo containerInfo = mock(ContainerInfo.class);

        when(this.endpointBuilder.getInspectContainerEndpoint(containerId)).thenReturn(inspectContainerEndpoint);
        when(this.restTemplate.getForObject(inspectContainerEndpoint, ContainerInfo.class)).thenReturn(containerInfo);

        assertThat(this.client.inspectContainer(containerId), is(containerInfo));
    }
}