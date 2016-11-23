package com.cargowhale.docker.container.info.integration;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ContainerInfoControllerGetLogsIT {

    @MockBean
    private RestTemplate restTemplate;

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private CargoWhaleProperties properties;

    @Test
    public void getLogsReturnsCorrectLogs() {
        String dockerUri = this.properties.getDockerUri();
        String container = "TestContainer";
        String logs = "These are definitely logs";
        String queries = "/logs?follow=0&stdout=1&stderr=1&since=0&timestamps=1&tail=100";

        when(this.restTemplate.getForObject(dockerUri + "/v1.24/containers/" + container + queries, String.class))
                .thenReturn(logs);

        ResponseEntity<String> response = exchange("/api/containers/" + container + queries);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));

        String returnedLogs = response.getBody();
        assertThat(returnedLogs, is(logs));
    }

    private ResponseEntity<String> exchange(final String url) {
        HttpEntity<?> requestEntity = null;
        ParameterizedTypeReference<String> typeReference = new ParameterizedTypeReference<String>() {
        };

        return this.client.exchange(url, HttpMethod.GET, requestEntity, typeReference, Collections.emptyMap());
    }
}
