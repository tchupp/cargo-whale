package com.cargowhale.docker.security;

import com.cargowhale.docker.config.security.AuthoritiesConstants;
import com.cargowhale.docker.config.security.jwt.JwtProperties;
import com.cargowhale.docker.config.security.jwt.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import groovy.json.JsonOutput;
import io.jsonwebtoken.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerIT {

    private static final String TEST_USER_GOOD = "testuser";
    private static final String TEST_PASS_GOOD = "testpass";
    private static final String TEST_USER_BAD = "testuser bad";

    @Autowired
    private MockMvc client;

    @Autowired
    private JwtProperties properties;

    @Test
    public void authenticationSuccessful() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        UserCredentials userCredentials = new UserCredentials(TEST_USER_GOOD, TEST_PASS_GOOD);

        String expectedAuthorities = Stream.of(AuthoritiesConstants.ADMIN, AuthoritiesConstants.USER).collect(Collectors.joining(","));
        long expiration = (new Date()).getTime() + (this.properties.getTokenValidityInSeconds() * 1000);

        MockHttpServletRequestBuilder requestBuilder = post("/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonOutput.toJson(userCredentials));
        MvcResult mvcResult = this.client.perform(requestBuilder)
            .andExpect(status().isOk())
            .andReturn();

        JWTToken jwtToken = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), JWTToken.class);

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(this.properties.getSecret()).parseClaimsJws(jwtToken.getToken());

        JwsHeader header = claimsJws.getHeader();
        assertThat(SignatureAlgorithm.forName(header.getAlgorithm()), is(SignatureAlgorithm.HS512));

        Claims body = claimsJws.getBody();
        assertThat(body.getSubject(), is(TEST_USER_GOOD));
        assertThat(body.get(TokenProvider.AUTHORITIES_KEY), is(expectedAuthorities));
        assertThat(body.getExpiration().getTime(), lessThan(expiration));
    }

    @Test
    public void authenticationFailed() throws Exception {
        UserCredentials userCredentials = new UserCredentials(TEST_USER_BAD, TEST_PASS_GOOD);

        MockHttpServletRequestBuilder requestBuilder = post("/api/authenticate")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonOutput.toJson(userCredentials));
        this.client.perform(requestBuilder)
            .andExpect(status().isUnauthorized());
    }
}
