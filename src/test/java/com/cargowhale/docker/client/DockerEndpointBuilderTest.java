package com.cargowhale.docker.client;

import com.cargowhale.docker.client.containers.management.state.ContainerChangeState;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(MockitoJUnitRunner.class)
public class DockerEndpointBuilderTest {

    @InjectMocks
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void getListContainersEndpointReturnsCorrectUri() throws Exception {
        String expectedUri = "/v1.24/containers/json";

        assertThat(this.endpointBuilder.getListContainersEndpoint(), is(expectedUri));
    }

    @Test
    public void getInspectContainerEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/json";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/json";

        assertThat(this.endpointBuilder.getInspectContainerEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getInspectContainerEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerLogsEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/logs";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/logs";

        assertThat(this.endpointBuilder.getContainerLogByIdEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerLogByIdEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerProcessesEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/top";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/top";

        assertThat(this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerChangeStateEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/start?t=5";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/restart?t=5";

        assertThat(this.endpointBuilder.getContainerChangeStateEndpoint(containerId1, ContainerChangeState.START), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerChangeStateEndpoint(containerId2, ContainerChangeState.RESTART), is(expectedUri2));
    }
}