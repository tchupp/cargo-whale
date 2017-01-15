package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
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

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;
import static com.cargowhale.docker.client.containers.ListContainersParam.state;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.web.util.UriComponentsBuilder.fromPath;

@RunWith(MockitoJUnitRunner.class)
public class ListContainersClientTest {
    private static final ParameterizedTypeReference<List<Container>> RESPONSE_TYPE = new ParameterizedTypeReference<List<Container>>() {
    };

    @InjectMocks
    private ListContainersClient client;

    @Mock
    private DockerRestTemplate restTemplate;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void listContainers_WithParams() throws Exception {
        List<Container> containers = Arrays.asList(mock(Container.class), mock(Container.class));

        String listContainersEndpoint = "/containers/json";
        UriComponentsBuilder builder = fromPath(listContainersEndpoint);
        builder.queryParam("all", "1");

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainersEndpoint);
        when(this.restTemplate.getForObject(builder.toUriString(), RESPONSE_TYPE)).thenReturn(containers);

        List<Container> actual = this.client.listContainers(allContainers());
        verify(this.restTemplate).getForObject(builder.toUriString(), RESPONSE_TYPE);

        assertThat(actual, is(containers));
    }

    @Test
    public void listContainers_WithFilterParams() throws Exception {
        List<Container> containers = Arrays.asList(mock(Container.class), mock(Container.class));
        String filterJson = "{\"status\":[\"created\"]}";

        String listContainersEndpoint = "/containers/json";
        UriComponentsBuilder builder = fromPath(listContainersEndpoint);
        builder.queryParam("filters", "{filters}");

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainersEndpoint);
        when(this.restTemplate.getForObject(builder.build().toString(), RESPONSE_TYPE, filterJson)).thenReturn(containers);

        List<Container> actual = this.client.listContainers(state(ContainerState.CREATED));

        assertThat(actual, is(containers));
    }

    @Test
    public void listContainers_WithBothParams() throws Exception {
        List<Container> containers = Arrays.asList(mock(Container.class), mock(Container.class));
        String filterJson = "{\"status\":[\"paused\"]}";

        String listContainersEndpoint = "/containers/json";
        UriComponentsBuilder builder = fromPath(listContainersEndpoint);
        builder.queryParam("all", "0");
        builder.queryParam("filters", "{filters}");

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainersEndpoint);
        when(this.restTemplate.getForObject(builder.build().toString(), RESPONSE_TYPE, filterJson)).thenReturn(containers);

        List<Container> actual = this.client.listContainers(state(ContainerState.PAUSED), allContainers(false));

        assertThat(actual, is(containers));
    }
}