package com.cargowhale.docker.client;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DockerEndpointBuilderTest {

    private static final String DOCKER_URI = "http://this.is.docker:yo";

    @InjectMocks
    private DockerEndpointBuilder endpointBuilder;

    @Mock
    private CargoWhaleProperties properties;

    @Test
    public void getContainerEndpointReturnsCorrectUri() throws Exception {
        String expectedUri = DOCKER_URI + "/v1.24/containers/json";
        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        assertThat(this.endpointBuilder.getContainersInfoEndpoint(), is(expectedUri));
    }

    @Test
    public void getContainerByIdEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = DOCKER_URI + "/v1.24/containers/" + containerId1 + "/json";
        String expectedUri2 = DOCKER_URI + "/v1.24/containers/" + containerId2 + "/json";

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        assertThat(this.endpointBuilder.getContainerInfoByIdEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerInfoByIdEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void setContainerByIdEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = DOCKER_URI + "/v1.24/containers/" + containerId1;
        String expectedUri2 = DOCKER_URI + "/v1.24/containers/" + containerId2;

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        assertThat(this.endpointBuilder.setContainerByIdEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.setContainerByIdEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerLogByIdEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = DOCKER_URI + "/v1.24/containers/" + containerId1 + "/logs";
        String expectedUri2 = DOCKER_URI + "/v1.24/containers/" + containerId2 + "/logs";

        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        assertThat(this.endpointBuilder.getContainerLogByIdEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerLogByIdEndpoint(containerId2), is(expectedUri2));
    }
}