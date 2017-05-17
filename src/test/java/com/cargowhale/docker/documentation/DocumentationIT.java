package com.cargowhale.docker.documentation;

import com.cargowhale.division.raml.RamlSpecBuilder;
import com.cargowhale.division.raml.model.RamlSpec;
import com.cargowhale.docker.test.integration.RamlSpecFiles;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentationIT {

    private final Logger log = LoggerFactory.getLogger(DocumentationIT.class);

    @Autowired
    private MockMvc client;

    @Test
    public void testAllEndpointsAreDocumented() throws Exception {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE);
        RamlSpec ramlSpec = RamlSpecBuilder.fromRamlApi10(ramlModelResult, RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE);

        ObjectMapper objectMapper = new ObjectMapper();
        MvcResult mvcResult = this.client.perform(get("/mappings")).andReturn();

        UserDefinedEndpoints userDefinedEndpoints = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDefinedEndpoints.class);

        MultiValueMap<String, HttpMethod> undocumentedEndpoints = new LinkedMultiValueMap<>();
        for (final Map.Entry<String, List<HttpMethod>> endpoint : userDefinedEndpoints.getEndpoints().entrySet()) {
            for (final HttpMethod method : endpoint.getValue()) {
                String path = endpoint.getKey();

                try {
                    ramlSpec.findResponses(path, method);
                    this.log.info(String.format("Path : '%1s' with Method: '%2s' found in RamlSpec: '%3s'", path, method, RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE));
                } catch (final Exception e) {
                    undocumentedEndpoints.add(path, method);
                }
            }
        }

        if (!undocumentedEndpoints.isEmpty()) {
            for (final Map.Entry<String, List<HttpMethod>> endpoint : undocumentedEndpoints.entrySet()) {
                for (final HttpMethod method : endpoint.getValue()) {
                    String path = endpoint.getKey();
                    this.log.error(String.format("Path : '%1s' with Method: '%2s' mapped in application but missing from RamlSpec: '%3s'", path, method, RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE));
                }
            }

            fail(String.format("Found: %1d endpoint(s) not documented in RamlSpec: '%2s'", undocumentedEndpoints.size(), RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE));
        }
    }
}
