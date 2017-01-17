package com.cargowhale.docker.container.info.index;

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

        assertThat(this.resourceProcessor.process(containerResource), sameInstance(containerResource));
    }

    @Test
    public void addsLinksToContainerResource() throws Exception {
        String containerId = "container_has_id!";
        ContainerResource containerResource = this.resourceProcessor.process(new ContainerResource(containerId));

        assertThat(containerResource.getLinks(), hasSize(5));

        verifyLink(containerResource, "up", "/api/containers");
        verifyLink(containerResource, Link.REL_SELF, "/api/containers/" + containerId);
        verifyLink(containerResource, "logs", "/api/containers/" + containerId + "/logs");
        verifyLink(containerResource, "stats", "/api/containers/" + containerId + "/stats");
        verifyLink(containerResource, "top", "/api/containers/" + containerId + "/top");
    }
}