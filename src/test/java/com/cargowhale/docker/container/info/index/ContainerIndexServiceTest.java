package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.spotify.docker.client.DockerClient.ListContainersFilterParam;
import com.spotify.docker.client.DockerClient.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.assertj.core.util.Arrays.array;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexServiceTest {

    @InjectMocks
    private ContainerIndexService service;

    @Mock
    private ListContainersClient client;

    @Mock
    private ContainerIndexBuilder builder;

    @Test
    public void getContainerIndex_NoParams_ReturnsContainerIndex() throws Exception {
        List<Container> containers = newArrayList(mock(Container.class));
        ContainerIndexResource containerIndex = mock(ContainerIndexResource.class);

        when(this.client.listContainers()).thenReturn(containers);
        when(this.builder.buildContainerIndex(containers)).thenReturn(containerIndex);

        assertThat(this.service.getContainerIndex(), is(containerIndex));
    }

    @Test
    public void getContainerIndex_WithParams_ReturnsContainerIndex() throws Exception {
        List<Container> containers = newArrayList(mock(Container.class));
        ContainerIndexResource containerIndex = mock(ContainerIndexResource.class);

        when(this.client.listContainers(any(ListContainersParam[].class))).thenReturn(containers);
        when(this.builder.buildContainerIndex(containers)).thenReturn(containerIndex);

        assertThat(this.service.getContainerIndex(array(ContainerState.EXITED)), is(containerIndex));
    }

    @Test
    public void getContainerIndex_WithParams_CorrectlyTransformsParams() throws Exception {
        ContainerIndexResource containerIndex = mock(ContainerIndexResource.class);

        when(this.client.listContainers(any(ListContainersParam[].class))).thenReturn(newArrayList());
        when(this.builder.buildContainerIndex(anyListOf(Container.class))).thenReturn(containerIndex);

        this.service.getContainerIndex(array(ContainerState.RUNNING, ContainerState.DEAD));

        ArgumentCaptor<ListContainersFilterParam> captor = forClass(ListContainersFilterParam.class);
        verify(this.client).listContainers(captor.capture());

        List<ListContainersFilterParam> allValues = captor.getAllValues();
        assertThat(allValues, hasSize(2));
        verifyParamEqual(new ListContainersFilterParam("status", "running"), allValues.get(0));
        verifyParamEqual(new ListContainersFilterParam("status", "dead"), allValues.get(1));
    }

    private void verifyParamEqual(final ListContainersParam expected, final ListContainersParam actual) {
        assertThat(actual.name(), is(expected.name()));
        assertThat(actual.value(), is(expected.value()));
    }
}