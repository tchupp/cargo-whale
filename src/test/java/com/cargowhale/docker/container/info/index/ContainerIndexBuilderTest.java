package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexBuilderTest {

    @InjectMocks
    private ContainerIndexBuilder builder;

    @Mock
    private ContainerMapper mapper;

    @Test
    public void returnsContainerIndexWithContainerResourceList() throws Exception {
        List<Container> containers = new ArrayList<>();
        List<ContainerResource> containerResources = new ArrayList<>();

        when(this.mapper.toResources(containers)).thenReturn(containerResources);

        ContainerIndexResource containerIndex = this.builder.buildContainerIndex(containers);

        assertThat(containerIndex.getContainers(), is(containerResources));
    }

    @Test
    public void returnsContainerIndexWithMapOfAllContainerStates() throws Exception {
        ContainerIndexResource containerIndex = this.builder.buildContainerIndex(new ArrayList<>());
        Map<ContainerState, Integer> stateMetadata = containerIndex.getStateMetadata();

        assertThat(stateMetadata.size(), is(7));
        assertThat(stateMetadata, hasKey(ContainerState.ALL));
        assertThat(stateMetadata, hasKey(ContainerState.CREATED));
        assertThat(stateMetadata, hasKey(ContainerState.RESTARTING));
        assertThat(stateMetadata, hasKey(ContainerState.RUNNING));
        assertThat(stateMetadata, hasKey(ContainerState.PAUSED));
        assertThat(stateMetadata, hasKey(ContainerState.EXITED));
        assertThat(stateMetadata, hasKey(ContainerState.DEAD));
    }

    @Test
    public void returnsContainerIndexWithMap_CorrectCountOfContainerStates_Empty() throws Exception {
        ContainerIndexResource containerIndex = this.builder.buildContainerIndex(new ArrayList<>());
        Map<ContainerState, Integer> stateMetadata = containerIndex.getStateMetadata();

        assertThat(stateMetadata.size(), is(7));
        assertThat(stateMetadata.get(ContainerState.ALL), is(0));
        assertThat(stateMetadata.get(ContainerState.CREATED), is(0));
        assertThat(stateMetadata.get(ContainerState.RESTARTING), is(0));
        assertThat(stateMetadata.get(ContainerState.RUNNING), is(0));
        assertThat(stateMetadata.get(ContainerState.PAUSED), is(0));
        assertThat(stateMetadata.get(ContainerState.EXITED), is(0));
        assertThat(stateMetadata.get(ContainerState.DEAD), is(0));
    }

    @Test
    public void returnsContainerIndexWithMap_CorrectCountOfContainerStates_NotEmpty() throws Exception {
        List<Container> containerList = new ArrayList<>();
        containerList.add(buildContainerWithState(ContainerState.CREATED));
        containerList.add(buildContainerWithState(ContainerState.CREATED));
        containerList.add(buildContainerWithState(ContainerState.RESTARTING));
        containerList.add(buildContainerWithState(ContainerState.RUNNING));
        containerList.add(buildContainerWithState(ContainerState.RUNNING));
        containerList.add(buildContainerWithState(ContainerState.RUNNING));
        containerList.add(buildContainerWithState(ContainerState.PAUSED));
        containerList.add(buildContainerWithState(ContainerState.EXITED));
        containerList.add(buildContainerWithState(ContainerState.DEAD));
        containerList.add(buildContainerWithState(ContainerState.DEAD));
        containerList.add(buildContainerWithState(ContainerState.DEAD));

        ContainerIndexResource containerIndex = this.builder.buildContainerIndex(containerList);
        Map<ContainerState, Integer> stateMetadata = containerIndex.getStateMetadata();

        assertThat(stateMetadata.size(), is(7));
        assertThat(stateMetadata.get(ContainerState.ALL), is(11));
        assertThat(stateMetadata.get(ContainerState.CREATED), is(2));
        assertThat(stateMetadata.get(ContainerState.RESTARTING), is(1));
        assertThat(stateMetadata.get(ContainerState.RUNNING), is(3));
        assertThat(stateMetadata.get(ContainerState.PAUSED), is(1));
        assertThat(stateMetadata.get(ContainerState.EXITED), is(1));
        assertThat(stateMetadata.get(ContainerState.DEAD), is(3));
    }

    private Container buildContainerWithState(final ContainerState state) {
        Container container = new Container();
        ReflectionTestUtils.setField(container, "state", state.getState());
        return container;
    }
}