package com.cargowhale.docker.security;

import com.cargowhale.docker.config.security.jwt.TokenProvider;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationControllerTest {

    @InjectMocks
    private AuthenticationController controller;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    public void authenticateReturnsJWTToken_WithAuthToken() throws Exception {
        UserCredentials credentials = new UserCredentials("username", "password");
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        Authentication authentication = mock(Authentication.class);
        String token = RandomStringUtils.random(5);

        when(this.authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(this.tokenProvider.createToken(authentication)).thenReturn(token);

        assertThat(this.controller.authenticate(credentials), is(new JWTToken(token)));
    }
}