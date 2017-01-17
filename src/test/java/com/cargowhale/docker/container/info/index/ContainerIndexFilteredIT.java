package com.cargowhale.docker.container.info.index;

import com.cargowhale.division.MockServiceBuilder;
import com.cargowhale.docker.container.info.ContainerState;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static com.cargowhale.division.matchers.RequestSpecMatcher.responseIsInSpec;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ContainerIndexFilteredIT {

    @Autowired
    private MockServiceBuilder dockerServiceBuilder;

    @Autowired
    private MockMvc client;

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
    public void getFilteredContainers_Dead() throws Exception {
        verifySingleStateFilter(ContainerState.DEAD);
    }

    private void verifySingleStateFilter(final ContainerState containerState) throws Exception {
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/json?filters={\"status\":[\"" + containerState.getState() + "\"]}", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);

        this.client.perform(get("/api/containers?filters=" + containerState.getState()))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with("/api/containers", HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON, containerState.getState()));
    }

    @Test
    public void verifyBadFilterReturnsHttpBadRequest() throws Exception {
        String state = "I_AM_A_TEAPOT";
        this.client.perform(get("/api/containers?filters=" + state))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with("/api/containers", HttpMethod.GET, HttpStatus.BAD_REQUEST, MediaTypes.HAL_JSON));
    }
}
