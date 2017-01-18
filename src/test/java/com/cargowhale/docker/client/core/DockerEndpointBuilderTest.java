package com.cargowhale.docker.client.core;

import com.cargowhale.docker.container.management.ContainerChangeState;
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

        assertThat(this.endpointBuilder.getContainerLogsEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerLogsEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerProcessesEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/top";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/top";

        assertThat(this.endpointBuilder.getContainerProcessesEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerProcessesEndpoint(containerId2), is(expectedUri2));
    }

    @Test
    public void getContainerChangeStateEndpointReturnsCorrectUri() throws Exception {
        ContainerChangeState state1 = ContainerChangeState.START;
        ContainerChangeState state2 = ContainerChangeState.RESTART;
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/start";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/restart";

        assertThat(this.endpointBuilder.getContainerChangeStateEndpoint(containerId1, state1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerChangeStateEndpoint(containerId2, state2), is(expectedUri2));
    }

    @Test
    public void getContainerStatsEndpointReturnsCorrectUri() throws Exception {
        String containerId1 = "1fds78i1h";
        String containerId2 = "4y712yui4";

        String expectedUri1 = "/v1.24/containers/" + containerId1 + "/stats?stream=0";
        String expectedUri2 = "/v1.24/containers/" + containerId2 + "/stats?stream=0";

        assertThat(this.endpointBuilder.getContainerStatsEndpoint(containerId1), is(expectedUri1));
        assertThat(this.endpointBuilder.getContainerStatsEndpoint(containerId2), is(expectedUri2));
    }
}