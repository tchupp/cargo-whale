package com.cargowhale.docker.container.info;

import com.cargowhale.docker.container.ContainerState;
import com.cargowhale.docker.container.info.model.ContainerIndex;
import com.cargowhale.docker.container.info.model.ContainerSummary;
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
    public void returnsContainerIndexWithContainerSummaryList() throws Exception {
        List<ContainerSummary> containerSummaryList = new ArrayList<>();
        ContainerIndex containerIndex = this.builder.buildContainerIndex(containerSummaryList);

        assertThat(containerIndex.getContainers(), sameInstance(containerSummaryList));
    }

    @Test
    public void returnsContainerIndexWithMapOfAllContainerStates() throws Exception {
        ContainerIndex containerIndex = this.builder.buildContainerIndex(new ArrayList<>());
        Map<ContainerState, Integer> stateMetadata = containerIndex.getStateMetadata();

        assertThat(stateMetadata.size(), is(6));
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

        assertThat(stateMetadata.size(), is(6));
        assertThat(stateMetadata.get(ContainerState.CREATED), is(0));
        assertThat(stateMetadata.get(ContainerState.RESTARTING), is(0));
        assertThat(stateMetadata.get(ContainerState.RUNNING), is(0));
        assertThat(stateMetadata.get(ContainerState.PAUSED), is(0));
        assertThat(stateMetadata.get(ContainerState.EXITED), is(0));
        assertThat(stateMetadata.get(ContainerState.DEAD), is(0));
    }

    @Test
    public void returnsContainerIndexWithMap_CorrectCountOfContainerStates_NotEmpty() throws Exception {
        List<ContainerSummary> containerSummaryList = new ArrayList<>();
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.CREATED));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.CREATED));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.RESTARTING));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.RUNNING));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.RUNNING));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.RUNNING));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.PAUSED));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.EXITED));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.DEAD));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.DEAD));
        containerSummaryList.add(new ContainerSummary("", new ArrayList<>(), "", "", ContainerState.DEAD));

        ContainerIndex containerIndex = this.builder.buildContainerIndex(containerSummaryList);
        Map<ContainerState, Integer> stateMetadata = containerIndex.getStateMetadata();

        assertThat(stateMetadata.size(), is(6));
        assertThat(stateMetadata.get(ContainerState.CREATED), is(2));
        assertThat(stateMetadata.get(ContainerState.RESTARTING), is(1));
        assertThat(stateMetadata.get(ContainerState.RUNNING), is(3));
        assertThat(stateMetadata.get(ContainerState.PAUSED), is(1));
        assertThat(stateMetadata.get(ContainerState.EXITED), is(1));
        assertThat(stateMetadata.get(ContainerState.DEAD), is(3));
    }
}