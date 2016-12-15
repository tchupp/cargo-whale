package com.cargowhale.docker.test.integration;

import com.cargowhale.division.raml.RamlSpec;
import com.cargowhale.division.raml.RamlSpecBuilder;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import static com.cargowhale.division.matchers.JsonMatcher.equalToJsonString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BaseIntegrationTest {

    protected void verifyResponse(final ResponseEntity<String> response, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        final RamlModelResult cargoWhaleRamlModelResult = new RamlModelBuilder().buildApi(RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE);
        final RamlSpec cargoWhaleRamlSpec = RamlSpecBuilder.fromRamlApi10(cargoWhaleRamlModelResult, RamlSpecFiles.CARGO_WHALE_RAML_SPEC_FILE);

        final String example = cargoWhaleRamlSpec.findExample(path, method, status, mediaType);

        assertThat(response.getStatusCode(), is(status));
        assertThat(response.getBody(), equalToJsonString(example));
    }
}
