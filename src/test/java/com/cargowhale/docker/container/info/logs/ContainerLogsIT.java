package com.cargowhale.docker.container.info.logs;

import com.cargowhale.division.MockServiceBuilder;
import com.cargowhale.docker.test.integration.RamlSpecFiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.cargowhale.division.matchers.RequestSpecMatcher.responseIsInSpec;
import static com.cargowhale.docker.test.integration.TestAuthenticationConstants.TEST_USER_AUTH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContainerLogsIT {

    private static final String CONTAINER_ID = "f911b0f4e0b19e3da3bae6dcff82195";
    private static final String CONTAINER_LOGS_ENDPOINT = "/api/containers/{id}/logs";

    @Autowired
    private MockMvc client;

    @Autowired
    private MockServiceBuilder dockerServiceBuilder;

    @Test
    public void getContainerLogs() throws Exception {
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/f911b0f4e0b19e3da3bae6dcff82195/logs?details=false&follow=false&stdout=true&stderr=true&timestamps=true&since=0&tail=100", HttpMethod.GET, HttpStatus.OK, MediaType.TEXT_PLAIN);

        this.client.perform(get(CONTAINER_LOGS_ENDPOINT, CONTAINER_ID).with(TEST_USER_AUTH))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with(CONTAINER_LOGS_ENDPOINT, HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON));

        this.dockerServiceBuilder.reset();
    }

    @Test
    public void getContainerLogs_NoAuthentication() throws Exception {
        this.client.perform(get(CONTAINER_LOGS_ENDPOINT, CONTAINER_ID))
            .andExpect(status().isUnauthorized());
    }
}
