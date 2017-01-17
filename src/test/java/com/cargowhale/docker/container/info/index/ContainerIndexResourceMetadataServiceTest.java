package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.container.info.ContainerState;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;
import static com.cargowhale.docker.test.ContainerTestUtilities.buildContainerWithState;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexResourceMetadataServiceTest {

    @InjectMocks
    private ContainerIndexResourceMetadataService service;

    @Mock
    private ListContainersClient client;

    @Test
    public void returnsMapOfAllContainerStates() throws Exception {
        when(this.client.listContainers(allContainers())).thenReturn(Collections.emptyList());
        Map<ContainerState, Integer> stateMetadata = this.service.getStateMetadata();

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
    public void returnsMap_CorrectCountOfContainerStates_Empty() throws Exception {
        when(this.client.listContainers(allContainers())).thenReturn(Collections.emptyList());
        Map<ContainerState, Integer> stateMetadata = this.service.getStateMetadata();

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
        List<Container> containerList = Arrays.asList(
            buildContainerWithState(ContainerState.CREATED),
            buildContainerWithState(ContainerState.CREATED),
            buildContainerWithState(ContainerState.RESTARTING),
            buildContainerWithState(ContainerState.RUNNING),
            buildContainerWithState(ContainerState.RUNNING),
            buildContainerWithState(ContainerState.RUNNING),
            buildContainerWithState(ContainerState.PAUSED),
            buildContainerWithState(ContainerState.EXITED),
            buildContainerWithState(ContainerState.DEAD),
            buildContainerWithState(ContainerState.DEAD),
            buildContainerWithState(ContainerState.DEAD)
        );
        when(this.client.listContainers(allContainers())).thenReturn(containerList);

        Map<ContainerState, Integer> stateMetadata = this.service.getStateMetadata();

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