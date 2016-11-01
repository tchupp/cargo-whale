package com.cargowhale.docker.client;

import com.cargowhale.docker.container.ContainerInfoVM;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContainerClientTest {

    private static final String DOCKER_ENDPOINT = "http://this.is.docker:yo";

    @InjectMocks
    private ContainerClient service;

    @Mock
    private RestTemplate template;

    @Mock
    private DockerEndpointCollection endpointCollection;

    @Mock
    private JsonConverter converter;

    @Test
    public void getAllContainersReturnsEveryContainerFromDockerApi() {
        final ContainerInfoVM[] containerInfoArray = Arrays.array(mock(ContainerInfoVM.class));

        when(this.endpointCollection.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?all=1", ContainerInfoVM[].class)).thenReturn(containerInfoArray);

        assertThat(this.service.getAllContainers(), contains(containerInfoArray));
    }

    @Test
    public void getFilteredContainersReturnsSelectedTypesOfContainers() {
        String filterJson = "json filter string";

        DockerContainerFilters filters = mock(DockerContainerFilters.class);
        final ContainerInfoVM[] containerInfoArray = Arrays.array(mock(ContainerInfoVM.class));

        when(this.endpointCollection.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.converter.toJson(filters)).thenReturn(filterJson);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?filters={filters}", ContainerInfoVM[].class, filterJson))
                .thenReturn(containerInfoArray);

        assertThat(this.service.getFilteredContainers(filters), contains(containerInfoArray));
    }

    @Test
    public void setContainerStatusSetsContainerToRunning() {
        String name = "testContainer";
        String status = "start";

        when(this.endpointCollection.getContainersEndpoint()).thenReturn(DOCKER_ENDPOINT);

        String actual = this.service.setContainerStatus(name, status);

        verify(this.template).postForObject(DOCKER_ENDPOINT + "/{name}/{status}", null, String.class, name, status);
        assertThat(actual, is(name));
    }
}

