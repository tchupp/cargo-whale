package com.cargowhale.docker.index;

import com.cargowhale.docker.test.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;

public class IndexResourceTest {

    @Before
    public void setup() {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void indexResourceHasContainerLink() throws Exception {
        IndexResource indexResource = new IndexResource();

        assertThat(indexResource.hasLink("containers"), is(true));
        assertThat(indexResource.getLink("containers").getHref(), endsWith("/api/containers"));
    }
}