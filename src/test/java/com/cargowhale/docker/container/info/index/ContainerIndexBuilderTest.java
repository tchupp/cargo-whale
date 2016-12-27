package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.client.containers.info.list.ContainerListItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexBuilderTest {

    @InjectMocks
    private ContainerIndexBuilder builder;

    @Test
    public void returnsContainerIndexWithContainerListResponseItemList() throws Exception {
        List<ContainerListItem> containerList = new ArrayList<>();
        ContainerIndex containerIndex = this.builder.buildContainerIndex(containerList);

        assertThat(containerIndex.getContainers(), sameInstance(containerList));
    }

    @Test
    public void returnsContainerIndexWithMapOfAllContainerStates() throws Exception {
        ContainerIndex containerIndex = this.builder.buildContainerIndex(new ArrayList<>());
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
        ContainerIndex containerIndex = this.builder.buildContainerIndex(new ArrayList<>());
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
        List<ContainerListItem> containerList = new ArrayList<>();
        containerList.add(new ContainerListItem(ContainerState.CREATED));
        containerList.add(new ContainerListItem(ContainerState.CREATED));
        containerList.add(new ContainerListItem(ContainerState.RESTARTING));
        containerList.add(new ContainerListItem(ContainerState.RUNNING));
        containerList.add(new ContainerListItem(ContainerState.RUNNING));
        containerList.add(new ContainerListItem(ContainerState.RUNNING));
        containerList.add(new ContainerListItem(ContainerState.PAUSED));
        containerList.add(new ContainerListItem(ContainerState.EXITED));
        containerList.add(new ContainerListItem(ContainerState.DEAD));
        containerList.add(new ContainerListItem(ContainerState.DEAD));
        containerList.add(new ContainerListItem(ContainerState.DEAD));

        ContainerIndex containerIndex = this.builder.buildContainerIndex(containerList);
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
}