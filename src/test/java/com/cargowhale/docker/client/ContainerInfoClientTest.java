package com.cargowhale.docker.client;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.client.info.ContainerInfoClient;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import com.cargowhale.docker.container.info.model.ContainerSummary;
import com.cargowhale.docker.container.info.model.DockerContainerProcessIndex;
import com.cargowhale.docker.util.JsonConverter;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerInfoClientTest {

    private static final String DOCKER_ENDPOINT = "http://this.is.docker:471828/";

    @InjectMocks
    private ContainerInfoClient client;

    @Mock
    private DockerRestTemplate template;

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
        String containerId = "container_id_yo";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.endpointBuilder.getContainerInfoByIdEndpoint(containerId)).thenReturn(DOCKER_ENDPOINT + containerId);
        when(this.template.getForObject(DOCKER_ENDPOINT + containerId, ContainerDetails.class)).thenReturn(containerDetails);

        assertThat(this.client.getContainerDetailsById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsByIdReturnsCorrectContainerLogs() throws Exception {
        String containerId = "thisId";
        String logsPath = "logs_path";

        boolean details = true;
        boolean follow = false;
        boolean stdOut = true;
        boolean stdErr = true;
        boolean timestamps = true;
        String since = "0";
        String tail = "265";
        LogFilters filters = new LogFilters(details, follow, stdOut, stdErr, timestamps, since, tail);

        String logs = "These are some fancy logs!";

        String formattedParams = String.format("?details=%s&follow=%s&stdout=%s&stderr=%s&timestamps=%s&since=%s&tail=%s", details, follow, stdOut, stdErr, timestamps, since, tail);

        when(this.endpointBuilder.getContainerLogByIdEndpoint(containerId)).thenReturn(/*DOCKER_ENDPOINT + */logsPath);
        when(this.template.getForObject(/*DOCKER_ENDPOINT + */logsPath + formattedParams, String.class)).thenReturn(logs);

        ContainerLogs containerLogs = this.client.getContainerLogsById(containerId, filters);
        assertThat(containerLogs.getLogs(), is(logs));
    }

    @Test
    public void getContainerProcessesByIdReturnsCorrectContainerProcesses() {
        String containerId = "thisId";
        DockerContainerProcessIndex dockerIndex = Mockito.mock(DockerContainerProcessIndex.class);
        List<String> process = Lists.newArrayList("PROCESS");
        List<List<String>> processes = Lists.newArrayList();
        processes.add(process);

        when(this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId)).thenReturn(DOCKER_ENDPOINT + containerId);
        when(this.template.getForObject(DOCKER_ENDPOINT + containerId, DockerContainerProcessIndex.class)).thenReturn(dockerIndex);
        when(dockerIndex.getProcesses()).thenReturn(processes);

        DockerContainerProcessIndex actual = this.client.getContainerProcessesById(containerId);
        assertThat(actual.getProcesses(), is(processes));
    }
}

