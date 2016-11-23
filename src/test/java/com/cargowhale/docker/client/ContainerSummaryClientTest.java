package com.cargowhale.docker.client;

import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.util.JsonConverter;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerSummaryClientTest {

    private static final String DOCKER_ENDPOINT = "http://this.is.docker:yo";

    @InjectMocks
    private ContainerInfoClient client;

    @Mock
    private RestTemplate template;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Mock
    private JsonConverter converter;

    @Test
    public void getAllContainersReturnsEveryContainerFromDockerApi() {
        final ContainerSummary[] containerSummaryArray = Arrays.array(mock(ContainerSummary.class));

        when(this.endpointBuilder.getContainersInfoEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?all=1", ContainerSummary[].class)).thenReturn(containerSummaryArray);

        assertThat(this.client.getAllContainers(), contains(containerSummaryArray));
    }

    @Test
    public void getFilteredContainersReturnsSelectedTypesOfContainers() {
        String filterJson = "json filter string";

        DockerContainerFilters filters = mock(DockerContainerFilters.class);
        final ContainerSummary[] containerSummaryArray = Arrays.array(mock(ContainerSummary.class));

        when(this.endpointBuilder.getContainersInfoEndpoint()).thenReturn(DOCKER_ENDPOINT);
        when(this.converter.toJson(filters)).thenReturn(filterJson);
        when(this.template.getForObject(DOCKER_ENDPOINT + "?filters={filters}", ContainerSummary[].class, filterJson))
                .thenReturn(containerSummaryArray);

        assertThat(this.client.getFilteredContainers(filters), contains(containerSummaryArray));
    }

    @Test
    public void getContainerByIdReturnsCorrectContainer() throws Exception {
        String containerId = "container id yo";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.endpointBuilder.getContainerInfoByIdEndpoint(containerId)).thenReturn(DOCKER_ENDPOINT + containerId);
        when(this.template.getForObject(DOCKER_ENDPOINT + containerId, ContainerDetails.class)).thenReturn(containerDetails);

        assertThat(this.client.getContainerDetailsById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsByIdReturnsCorrectContainerLogs() throws Exception {
        String containerId = "container id yo";
        String follow = "0";
        String stdOut = "0";
        String stdErr = "0";
        String since = "0";
        String timestamps = "0";
        String tail = "0";
        String logs = "logs";

        String params = "follow=" + follow;
        params += "&stdout=" + stdOut;
        params += "&stderr=" + stdErr;
        params += "&since=" + since;
        params += "&timestamps=" + timestamps;
        params += "&tail=" + tail;

        when(this.endpointBuilder.getContainerLogByIdEndpoint(containerId)).thenReturn(DOCKER_ENDPOINT + containerId);
        when(this.template.getForObject(DOCKER_ENDPOINT + containerId + params, String.class)).thenReturn(logs);

        assertThat(this.client.getContainerLogsById(containerId, follow, stdOut, stdErr, since, timestamps, tail), is(logs));
    }

}

