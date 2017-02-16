package com.cargowhale.docker.test.integration;

import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.UserRequestPostProcessor;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

public abstract class TestAuthenticationConstants {

    private TestAuthenticationConstants() {
    }

    public static final String TEST_USER_NAME = "testuser";
    public static final String TEST_USER_PASS = "testpass";
    public static final String TEST_USER_NAME_BAD = "testuser_bad";

    public static final UserRequestPostProcessor TEST_USER_AUTH = user(TEST_USER_NAME);
}
