package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.client.containers.ContainerState;
import com.cargowhale.docker.test.ControllerTestUtils;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;

import static com.cargowhale.docker.client.containers.ListContainersParam.allContainers;
import static com.cargowhale.docker.client.containers.ListContainersParam.state;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerIndexControllerTest {

    @InjectMocks
    private ContainerIndexController controller;

    @Mock
    private ContainerIndexService service;

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void listContainersReturnsContainerIndexResource() {
        ContainerIndexResource containerIndex = new ContainerIndexResource();

        when(this.service.getContainerIndex(allContainers())).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(), is(containerIndex));
    }

    @Test
    public void listContainersAddsLinksToContainerIndexResource() {
        when(this.service.getContainerIndex(allContainers())).thenReturn(new ContainerIndexResource());

        ContainerIndexResource containerIndex = this.controller.listContainers();

        assertThat(containerIndex.getLinks(), hasSize(2));

        verifyLink(containerIndex, "up", "/api");
        verifyLink(containerIndex, Link.REL_SELF, "/api/containers");
    }

    @Test
    public void listContainersReturnsContainerIndexResource_WithParams() {
        ContainerIndexResource containerIndex = new ContainerIndexResource();

        when(this.service.getContainerIndex(state(ContainerState.RUNNING), state(ContainerState.DEAD))).thenReturn(containerIndex);

        assertThat(this.controller.listContainers(new ContainerState[]{ContainerState.RUNNING, ContainerState.DEAD}), is(containerIndex));
    }

    @Test
    public void listContainersAddsLinksToContainerIndexResource_WithParams() {
        when(this.service.getContainerIndex(state(ContainerState.EXITED))).thenReturn(new ContainerIndexResource());

        ContainerIndexResource containerIndex = this.controller.listContainers(ContainerState.EXITED);

        assertThat(containerIndex.getLinks(), hasSize(2));

        verifyLink(containerIndex, "up", "/api");
        verifyLink(containerIndex, Link.REL_SELF, "/api/containers");
    }

    private void verifyLink(final ContainerIndexResource containerIndex, final String rel, final String path) {
        assertThat(containerIndex.hasLink(rel), is(true));
        Link upLink = containerIndex.getLink(rel);
        assertThat(upLink.getHref(), Matchers.endsWith(path));
    }
}
