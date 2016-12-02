package com.cargowhale.docker.container.info;

import com.cargowhale.docker.client.ContainerInfoClient;
import com.cargowhale.docker.client.DockerContainerFilters;
import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.LogFilters;
import com.cargowhale.docker.container.StateFilters;
import com.cargowhale.docker.container.info.model.*;
import org.assertj.core.util.Arrays;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerInfoServiceTest {

    @InjectMocks
    private ContainerInfoService service;

    @Mock
    private ContainerInfoClient client;

    @Test
    public void getAllContainersReturnsAllContainersFromClient() {
        List<ContainerSummary> expectedContainerList = Collections.singletonList(mock(ContainerSummary.class));

        when(this.client.getAllContainers()).thenReturn(expectedContainerList);

        ContainerSummaryIndex actual = this.service.getAllContainers();

        assertThat(actual.getContainers(), is(expectedContainerList));
    }

    @Test
    public void getFilteredContainersReturnsFilteredContainers() {
        ContainerState[] containerStatuses = Arrays.array(ContainerState.DEAD, ContainerState.PAUSED);

        StateFilters stateFilters = new StateFilters(containerStatuses);
        DockerContainerFilters filters = new DockerContainerFilters(containerStatuses);
        List<ContainerSummary> expectedContainerList = Collections.singletonList(mock(ContainerSummary.class));

        when(this.client.getFilteredContainers(filters)).thenReturn(expectedContainerList);

        ContainerSummaryIndex actual = this.service.getContainersFilterByStatus(stateFilters);

        assertThat(actual.getContainers(), is(expectedContainerList));
    }

    @Test
    public void getContainerDetailsById() throws Exception {
        String containerId = "container id string";
        ContainerDetails containerDetails = mock(ContainerDetails.class);

        when(this.client.getContainerDetailsById(containerId)).thenReturn(containerDetails);

        assertThat(this.service.getContainerDetailsById(containerId), is(containerDetails));
    }

    @Test
    public void getContainerLogsById() throws Exception {
        String containerId = "container id string";

        String follow = "0";
        String stdOut = "1";
        String stdErr = "1";
        String since = "0";
        String timestamps = "0";
        String tail = "650";
        LogFilters filters = new LogFilters(follow, stdOut, stdErr, since, timestamps, tail);

        ContainerLogs containerLogs = mock(ContainerLogs.class);

        when(this.client.getContainerLogsById(containerId, filters)).thenReturn(containerLogs);

        assertThat(this.service.getContainerLogsById(containerId, filters), is(containerLogs));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndexWithEmptyListOfProcesses(){
        String containerId = "container id string";
        DockerContainerProcessIndex dockerIndex = Mockito.mock(DockerContainerProcessIndex.class);

        when(this.client.getContainerProcessesById(containerId)).thenReturn(dockerIndex);
        when(dockerIndex.getProcesses()).thenReturn(Lists.newArrayList());

        ContainerProcessIndex actual = this.service.getContainerProcessesById(containerId);
        assertThat(actual.getProcesses().size(), is(0));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndexWithOneProcess(){
        String containerId = "container id string";
        DockerContainerProcessIndex dockerIndex = Mockito.mock(DockerContainerProcessIndex.class);
        List<String> dockerProcess = Lists.newArrayList("root","12944","12927","0","20:42","?","00:00:00","/bin/sh -c java -jar /app.war");
        List<List<String>> dockerProcesses = Lists.newArrayList();
        dockerProcesses.add(dockerProcess);

        when(this.client.getContainerProcessesById(containerId)).thenReturn(dockerIndex);
        when(dockerIndex.getProcesses()).thenReturn(dockerProcesses);

        ContainerProcessIndex actual = this.service.getContainerProcessesById(containerId);
        assertThat(actual.getProcesses().size(), is(1));
        assertThat(actual.getProcesses().get(0).getPid(), is("12944"));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessIndexWithTwoProcesses(){
        String containerId = "container id string";
        DockerContainerProcessIndex dockerIndex = Mockito.mock(DockerContainerProcessIndex.class);
        List<String> dockerShellProcess = Lists.newArrayList("root","12944","12927","0","20:42","?","00:00:00","/bin/sh -c java -jar /app.war");
        List<String> dockerJavaProcess = Lists.newArrayList("root","12987","12944","13","20:42","?","00:00:28","java -jar /app.war");
        List<List<String>> dockerProcesses = Lists.newArrayList();
        dockerProcesses.add(dockerShellProcess);
        dockerProcesses.add(dockerJavaProcess);

        when(this.client.getContainerProcessesById(containerId)).thenReturn(dockerIndex);
        when(dockerIndex.getProcesses()).thenReturn(dockerProcesses);

        ContainerProcessIndex actual = this.service.getContainerProcessesById(containerId);
        assertThat(actual.getProcesses().size(), is(2));
        assertThat(actual.getProcesses().get(0).getPid(), is("12944"));
        assertThat(actual.getProcesses().get(1).getPid(), is("12987"));
    }

    @Test
    public void getContainerProcessesByIdReturnsContainerProcessWithCorrectValues(){
        String containerId = "container id string";
        DockerContainerProcessIndex dockerIndex = Mockito.mock(DockerContainerProcessIndex.class);
        List<String> dockerProcess = Lists.newArrayList("root","12944","12927","0","20:42","?","00:00:00","/bin/sh -c java -jar /app.war");
        List<List<String>> dockerProcesses = Lists.newArrayList();
        dockerProcesses.add(dockerProcess);

        when(this.client.getContainerProcessesById(containerId)).thenReturn(dockerIndex);
        when(dockerIndex.getProcesses()).thenReturn(dockerProcesses);

        ContainerProcessIndex actual = this.service.getContainerProcessesById(containerId);
        assertThat(actual.getProcesses().get(0).getUid(), is("root"));
        assertThat(actual.getProcesses().get(0).getPid(), is("12944"));
        assertThat(actual.getProcesses().get(0).getPpid(), is("12927"));
        assertThat(actual.getProcesses().get(0).getC(), is("0"));
        assertThat(actual.getProcesses().get(0).getsTime(), is("20:42"));
        assertThat(actual.getProcesses().get(0).getTty(), is("?"));
        assertThat(actual.getProcesses().get(0).getTime(), is("00:00:00"));
        assertThat(actual.getProcesses().get(0).getCmd(), is("/bin/sh -c java -jar /app.war"));
    }
}