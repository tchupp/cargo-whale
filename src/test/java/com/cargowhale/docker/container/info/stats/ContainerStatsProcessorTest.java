package com.cargowhale.docker.container.info.stats;

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
public class ContainerStatsProcessorTest {

    @InjectMocks
    private ContainerStatsProcessor processor;

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void returnsSameContainerStatsResource() throws Exception {
        final String containerId = "containerGV17";
        ContainerStatsResource statsResource = new ContainerStatsResource(containerId);

        assertThat(this.processor.process(statsResource), sameInstance(statsResource));
    }

    @Test
    public void addsLinksToContainerStatsResource() throws Exception {
        final String containerId = "containerGV17";

        ContainerStatsResource statsResource = this.processor.process(new ContainerStatsResource(containerId));

        assertThat(statsResource.getLinks(), hasSize(2));

        verifyLink(statsResource, "up", "/api/containers/" + containerId);
        verifyLink(statsResource, Link.REL_SELF, "/api/containers/" + containerId + "/stats");
    }
}