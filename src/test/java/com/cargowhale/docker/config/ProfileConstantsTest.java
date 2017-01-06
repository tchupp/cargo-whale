package com.cargowhale.docker.config;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class ProfileConstantsTest {

    @Test
    public void testConstants() throws Exception {
        assertThat(ProfileConstants.SPRING_PROFILE_DEVELOPMENT, is("dev"));
        assertThat(ProfileConstants.SPRING_PROFILE_PRODUCTION, is("prod"));
    }
}