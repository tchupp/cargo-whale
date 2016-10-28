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
public class DockerEndpointCollectionTest {

    private static final String DOCKER_URI = "http://this.is.docker:yo";

    @InjectMocks
    private DockerEndpointCollection endpointCollection;

    @Mock
    private CargoWhaleProperties properties;

    @Test
    public void getContainerEndpointReturnsCorrectUri() throws Exception {
        when(this.properties.getDockerUri()).thenReturn(DOCKER_URI);

        assertThat(this.endpointCollection.getContainersEndpoint(), is(DOCKER_URI + "/v1.24/containers/json"));
    }
}