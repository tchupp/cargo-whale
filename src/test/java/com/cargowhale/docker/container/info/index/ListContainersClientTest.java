package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@RunWith(MockitoJUnitRunner.class)
public class ListContainersClientTest {

    @InjectMocks
    private ListContainersClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void listContainers() throws Exception {
        ParameterizedTypeReference<List<Container>> responseType = new ParameterizedTypeReference<List<Container>>() {
        };
        List<Container> containers = Arrays.asList(mock(Container.class), mock(Container.class));

        String listContainersEndpoint = "/containers/json";
        UriComponentsBuilder builder = fromPath(listContainersEndpoint);
        builder.queryParam("all", "1");

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainersEndpoint);
        when(this.restTemplate.getForObject(builder.toUriString(), responseType)).thenReturn(containers);

        List<Container> actual = this.client.listContainers();

        assertThat(actual, is(containers));
    }
}