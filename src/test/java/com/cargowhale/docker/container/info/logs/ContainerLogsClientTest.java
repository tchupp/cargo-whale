package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.LogStream;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerLogsClientTest {

    @InjectMocks
    private ContainerLogsClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void getContainerLogStream() throws Exception {
        String containerLogsEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        LogFilters filters = new LogFilters(false, true, true, true, 0, "265");
        UriComponentsBuilder builder = UriComponentsBuilder.fromPath(containerLogsEndpoint).queryParams(filters.asQueryParameters());

        LogStream logStream = mock(LogStream.class);

        when(this.endpointBuilder.getContainerLogsEndpoint(containerId)).thenReturn(containerLogsEndpoint);
        when(this.restTemplate.getForObject(builder.toUriString(), LogStream.class)).thenReturn(logStream);

        LogStream actual = this.client.getContainerLogStream(containerId, filters);
        assertThat(actual, is(logStream));
    }
}

