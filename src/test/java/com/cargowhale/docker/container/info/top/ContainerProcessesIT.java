package com.cargowhale.docker.container.info.top;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ContainerProcessesIT {

    @Autowired
    private MockMvc client;

    @Autowired
    private MockServiceBuilder dockerServiceBuilder;

    @Test
    public void getContainerDetails() throws Exception {
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/f911b0f4e0b19e3da3bae6dcff82195/json", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);
        this.dockerServiceBuilder.expectRequest("/v1.24/containers/f911b0f4e0b19e3da3bae6dcff82195/top", HttpMethod.GET, HttpStatus.OK, MediaType.APPLICATION_JSON);

        this.client.perform(get("/api/containers/f911b0f4e0b19e3da3bae6dcff82195/top"))
            .andExpect(responseIsInSpec(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE)
                .with("/api/containers/{id}/top", HttpMethod.GET, HttpStatus.OK, MediaTypes.HAL_JSON));

        this.dockerServiceBuilder.reset();
    }
}
