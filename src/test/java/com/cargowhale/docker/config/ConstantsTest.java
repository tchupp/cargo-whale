package com.cargowhale.docker.config;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ConstantsTest {

    @Test
    public void testConstants() throws Exception {
        assertThat(Constants.SPRING_PROFILE_DEVELOPMENT, is("dev"));
        assertThat(Constants.SPRING_PROFILE_PRODUCTION, is("prod"));
    }
}