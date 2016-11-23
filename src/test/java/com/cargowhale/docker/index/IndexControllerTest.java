package com.cargowhale.docker.index;

import com.cargowhale.docker.test.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;

@RunWith(MockitoJUnitRunner.class)
public class IndexControllerTest {

    @InjectMocks
    private IndexController controller;

    @Before
    public void setup() {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void indexReturnsIndexResourceWithCorrectLinks() throws Exception {
        assertThat(this.controller.index(), instanceOf(IndexResource.class));
    }
}