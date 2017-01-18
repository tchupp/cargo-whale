package com.cargowhale.docker.container.info.top;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.TopResults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerTopClientTest {

    @InjectMocks
    private ContainerTopClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void containerTop() throws Exception {
        String containerTopEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        TopResults results = mock(TopResults.class);

        when(this.endpointBuilder.getContainerProcessesEndpoint(containerId)).thenReturn(containerTopEndpoint);
        when(this.restTemplate.getForObject(containerTopEndpoint, TopResults.class)).thenReturn(results);

        assertThat(this.client.containerTop(containerId), is(results));
    }
}