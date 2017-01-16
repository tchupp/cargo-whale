package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.test.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.cargowhale.docker.test.ControllerTestUtils.verifyLink;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexResourceAssemblerTest {

    @InjectMocks
    private ContainerIndexResourceAssembler resourceAssembler;

    @Mock
    private ContainerIndexResourceMetadataService service;

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void returnsContainerIndexResourceWithStateMetadata() throws Exception {
        Map<ContainerState, Integer> stateMetadata = Collections.emptyMap();
        when(this.service.getStateMetadata()).thenReturn(stateMetadata);
        List<ContainerResource> containerResources = asList(mock(ContainerResource.class), mock(ContainerResource.class));

        ContainerIndexResource resource = this.resourceAssembler.toResource(containerResources);

        assertThat(resource.getStateMetadata(), is(stateMetadata));
    }

    @Test
    public void returnsContainerIndexResourceWithLinks() throws Exception {
        when(this.service.getStateMetadata()).thenReturn(Collections.emptyMap());
        List<ContainerResource> containerResources = asList(mock(ContainerResource.class), mock(ContainerResource.class));

        ContainerIndexResource resource = this.resourceAssembler.toResource(containerResources);

        assertThat(resource.getLinks(), hasSize(8));

        verifyLink(resource, "up", "/api");
        verifyLink(resource, ContainerState.ALL.getState(), "/api/containers");
        verifyLink(resource, ContainerState.CREATED.getState(), "/api/containers?state=" + ContainerState.CREATED);
        verifyLink(resource, ContainerState.RESTARTING.getState(), "/api/containers?state=" + ContainerState.RESTARTING);
        verifyLink(resource, ContainerState.RUNNING.getState(), "/api/containers?state=" + ContainerState.RUNNING);
        verifyLink(resource, ContainerState.PAUSED.getState(), "/api/containers?state=" + ContainerState.PAUSED);
        verifyLink(resource, ContainerState.EXITED.getState(), "/api/containers?state=" + ContainerState.EXITED);
        verifyLink(resource, ContainerState.DEAD.getState(), "/api/containers?state=" + ContainerState.DEAD);
    }
}