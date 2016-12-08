package com.cargowhale.docker.client.containers.info;

import com.cargowhale.docker.client.DockerEndpointBuilder;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import com.cargowhale.docker.client.containers.info.list.ListContainerFilters;
import com.cargowhale.docker.client.containers.info.logs.LogFilters;
import com.cargowhale.docker.client.containers.info.top.ContainerTop;
import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.container.info.model.ContainerDetails;
import com.cargowhale.docker.container.info.model.ContainerLogs;
import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerInfoClientTest {

    @InjectMocks
    private ContainerInfoClient client;

    @Mock
    private DockerRestTemplate template;

    @Mock
    private DockerEndpointBuilder endpointBuilder;

    @Test
    public void listContainersReturnsEveryContainerFromDockerApi() {
        String listContainerEndpoint = UUID.randomUUID().toString();
        final ContainerListItem[] containerArray = Arrays.array(mock(ContainerListItem.class));

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainerEndpoint);
        when(this.template.getForObject(listContainerEndpoint + "?all=1", ContainerListItem[].class)).thenReturn(containerArray);

        assertThat(this.client.listContainers(), contains(containerArray));
    }

    @Test
    public void listContainersReturnsSelectedTypesOfContainers() {
        String listContainerEndpoint = UUID.randomUUID().toString();
        String filterJson = "json filter string";

        ListContainerFilters filters = mock(ListContainerFilters.class);
        final ContainerListItem[] containerArray = Arrays.array(mock(ContainerListItem.class));

        when(this.endpointBuilder.getListContainersEndpoint()).thenReturn(listContainerEndpoint);
        when(filters.toJson()).thenReturn(filterJson);
        when(this.template.getForObject(listContainerEndpoint + "?filters={filters}", ContainerListItem[].class, filterJson)).thenReturn(containerArray);

        assertThat(this.client.listContainers(filters), contains(containerArray));
    }

    @Test
    public void inspectContainerReturnsCorrectContainer() throws Exception {
        String inspectContainerEndpoint = UUID.randomUUID().toString();
        String containerId = "container_id_yo";

        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.endpointBuilder.getInspectContainerEndpoint(containerId)).thenReturn(inspectContainerEndpoint);
        when(this.template.getForObject(inspectContainerEndpoint, ContainerDetails.class)).thenReturn(containerDetails);

        assertThat(this.client.inspectContainer(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsReturnsCorrectContainerLogs() throws Exception {
        String containerLogsEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

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

        when(this.endpointBuilder.getContainerLogByIdEndpoint(containerId)).thenReturn(containerLogsEndpoint);
        when(this.template.getForObject(containerLogsEndpoint + formattedParams, String.class)).thenReturn(logs);

        ContainerLogs containerLogs = this.client.getContainerLogs(containerId, filters);
        assertThat(containerLogs.getLogs(), is(logs));
    }

    @Test
    public void getContainerProcessesReturnsCorrectContainerProcesses() {
        String containerTopEndpoint = UUID.randomUUID().toString();
        String containerId = "thisId";

        ContainerTop containerTop = Mockito.mock(ContainerTop.class);

        when(this.endpointBuilder.getContainerProcessesByIdEndpoint(containerId)).thenReturn(containerTopEndpoint);
        when(this.template.getForObject(containerTopEndpoint, ContainerTop.class)).thenReturn(containerTop);

        assertThat(this.client.getContainerProcesses(containerId), is(containerTop));
    }
}

