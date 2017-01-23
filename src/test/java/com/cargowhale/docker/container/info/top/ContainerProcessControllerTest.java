package com.cargowhale.docker.container.info.top;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.util.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerProcessControllerTest {

    @InjectMocks
    private ContainerProcessController controller;

    @Mock
    private ContainerProcessService processService;

    @Mock
    private ContainerProcessesResourceAssembler processesResourceAssembler;

    @Test
    public void getContainerProcessesById() {
        String containerId = "container id!";
        ContainerProcessIndex processes = new ContainerProcessIndex(containerId, newArrayList());
        ContainerProcessesResource processesResource = mock(ContainerProcessesResource.class);

        when(this.processService.getContainerProcesses(containerId)).thenReturn(processes);
        when(this.processesResourceAssembler.toResource(processes)).thenReturn(processesResource);

        assertThat(this.controller.getContainerProcesses(containerId), is(processesResource));
    }

}