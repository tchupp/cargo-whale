package com.cargowhale.docker.container.info.resource;

import com.cargowhale.division.MockServiceBuilder;
import com.cargowhale.docker.test.integration.RamlSpecFiles;
import org.junit.After;
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
public class ContainerIndexFilteredIT {

    private static final String CONTAINER_INDEX_ENDPOINT = "/api/containers";

    @Autowired
    private MockServiceBuilder dockerServiceBuilder;

    @Autowired
    private MockMvc client;

    @After
    public void tearDown() throws Exception {
        this.dockerServiceBuilder.reset();
    }

    @Test
    public void getFilteredContainers_Created() throws Exception {
        verifySingleStateFilter(ContainerState.CREATED);
    }

    @Test
    public void getFilteredContainers_Restarting() throws Exception {
        verifySingleStateFilter(ContainerState.RESTARTING);
    }

    @Test
    public void getFilteredContainers_Running() throws Exception {
        verifySingleStateFilter(ContainerState.RUNNING);
    }

    @Test
    public void getFilteredContainers_Paused() throws Exception {
        verifySingleStateFilter(ContainerState.PAUSED);
    }

    @Test
    public void getFilteredContainers_Exited() throws Exception {
        verifySingleStateFilter(ContainerState.EXITED);
    }

    @Test
    public void getFilteredContainers_Dead_Empty() throws Exception {
        final ContainerState containerState = ContainerState.DEAD;

        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?filters={\"status\":[\"" + containerState.getState() + "\"]}", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?all=1", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);

        this.client.perform(get(CONTAINER_INDEX_ENDPOINT + "?state=" + containerState.getState()).with(TEST_USER_AUTH))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with(CONTAINER_INDEX_ENDPOINT, HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON, "empty"));
    }

    private void verifySingleStateFilter(final ContainerState containerState) throws Exception {
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?filters={\"status\":[\"" + containerState.getState() + "\"]}", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/f911b0f4e0b19e3da3bae6dcff82195/json", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?all=1", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);

        this.client.perform(get(CONTAINER_INDEX_ENDPOINT + "?state=" + containerState.getState()).with(TEST_USER_AUTH))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with(CONTAINER_INDEX_ENDPOINT, HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON, containerState.getState()));
    }

    @Test
    public void badFilterReturnsHttpBadRequest() throws Exception {
        String state = "I_AM_A_TEAPOT";

        this.client.perform(get(CONTAINER_INDEX_ENDPOINT + "?state=" + state).with(TEST_USER_AUTH))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with(CONTAINER_INDEX_ENDPOINT, HttpMethod.GET, HttpStatus.BAD_REQUEST, MediaTypes.HAL_JSON));
    }

    @Test
    public void getFilteredContainers_NoAuthentication() throws Exception {
        this.client.perform(get(CONTAINER_INDEX_ENDPOINT))
            .andExpect(status().isUnauthorized());
    }
}
