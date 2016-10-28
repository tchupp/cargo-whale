package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
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
public class ContainerClientTest {

    private static final String DOCKER_URI = "http://this.is.docker:yo";

    @InjectMocks
    private ContainerClient service;

    @Mock
    private CargoWhaleProperties properties;

    @Mock
    private RestTemplate template;

    @Test
    public void getAllContainersReturnsEveryContainerFromDockerApi() {
        final String expectedContainers = "ALL CONTAINERS";

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);
        when(this.template.getForObject(DOCKER_URI + "/v1.24/containers/json?all=1", String.class))
                .thenReturn(expectedContainers);

        String actualContainers = this.service.getAllContainers();

        assertThat(actualContainers, is(expectedContainers));
    }

    @Test
    public void getFilteredContainersReturnsSelectedTypesOfContainers() {
        String expected = "ALL RUNNING CONTAINERS";
        String filter = "running";
        String filterString = createFilterString(filter);

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);
        when(this.template.getForObject(DOCKER_URI + "/v1.24/containers/json?filters={filterString}", String.class, filterString))
                .thenReturn(expected);

        String actual = this.service.getFilteredContainers(filter);

        assertThat(actual, is(expected));
    }

    @Test
    public void setContainerStatusSetsContainerToRunning() {
        String name = "testContainer";
        String status = "start";

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        String actual = this.service.setContainerStatus(name, status);

        verify(this.template).postForObject(DOCKER_URI + "/v1.24/containers/{name}/{status}", null, String.class, name, status);
        assertThat(actual, is(name));
    }

    private String createFilterString(String filter) {
        return "{\"status\":[\"" + filter + "\"]}";
    }
}

