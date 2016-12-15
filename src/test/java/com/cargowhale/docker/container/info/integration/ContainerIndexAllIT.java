package com.cargowhale.docker.container.info.integration;

import com.cargowhale.division.MockServiceBuilder;
import com.cargowhale.docker.test.integration.BaseIntegrationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ContainerIndexAllIT extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    @Qualifier("dockerServiceBuilder")
    private MockServiceBuilder dockerServiceBuilder;

    @Test
    public void getAllContainers() {
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?all=1", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);

        ResponseEntity<String> response = this.client.exchange("/api/containers", HttpMethod.GET, HttpEntity.EMPTY, String.class);

        verifyResponse(response, "/api/containers", HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON);
    }
}
