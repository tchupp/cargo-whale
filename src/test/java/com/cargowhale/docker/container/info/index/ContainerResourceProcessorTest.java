package com.cargowhale.docker.container.info.index;

import com.cargowhale.docker.container.info.index.ContainerResource.ContainerState;
import com.cargowhale.docker.test.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;

import static com.cargowhale.docker.test.ControllerTestUtils.verifyLink;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.sameInstance;

@RunWith(MockitoJUnitRunner.class)
public class ContainerResourceProcessorTest {

    @InjectMocks
    private ContainerResourceProcessor resourceProcessor;

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void returnsSameContainerResource() throws Exception {
        String containerId = "container_has_id!";
        ContainerResource containerResource = new ContainerResource(containerId);
        ContainerState containerState = new ContainerState();
        containerState.setRunning(true);
        containerResource.setState(containerState);

        assertThat(this.resourceProcessor.process(containerResource), sameInstance(containerResource));
    }

    @Test
    public void addsLinksToContainerResource() throws Exception {
        String containerId = "container_has_id!";

        ContainerResource preProcessResource = new ContainerResource(containerId);
        ContainerState containerState = new ContainerState();
        containerState.setRunning(true);
        preProcessResource.setState(containerState);

        ContainerResource containerResource = this.resourceProcessor.process(preProcessResource);

        assertThat(containerResource.getLinks(), hasSize(5));

        verifyLink(containerResource, "up", "/api/containers");
        verifyLink(containerResource, Link.REL_SELF, "/api/containers/" + containerId);
        verifyLink(containerResource, "logs", "/api/containers/" + containerId + "/logs");
        verifyLink(containerResource, "stats", "/api/containers/" + containerId + "/stats");
        verifyLink(containerResource, "top", "/api/containers/" + containerId + "/top");
    }

    @Test
    public void addsLinksToContainerResource_ContainerNotRunning() throws Exception {
        String containerId = "container_has_id!";

        ContainerResource preProcessResource = new ContainerResource(containerId);
        ContainerState containerState = new ContainerState();
        containerState.setRunning(false);
        preProcessResource.setState(containerState);

        ContainerResource containerResource = this.resourceProcessor.process(preProcessResource);

        assertThat(containerResource.getLinks(), hasSize(4));

        verifyLink(containerResource, "up", "/api/containers");
        verifyLink(containerResource, Link.REL_SELF, "/api/containers/" + containerId);
        verifyLink(containerResource, "logs", "/api/containers/" + containerId + "/logs");
        verifyLink(containerResource, "stats", "/api/containers/" + containerId + "/stats");
    }
}