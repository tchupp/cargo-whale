package com.cargowhale.docker.test.integration;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.cargowhale.docker.config.CargoWhaleProperties;
import org.junit.Before;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.client.MockRestServiceServer;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static com.cargowhale.docker.test.integration.RamlApiBuilder.flattenRaml;
import static com.cargowhale.docker.test.integration.RamlApiVerifier.verifyRamlModelResult;
import static com.cargowhale.docker.test.matchers.JsonMatcher.equalToJsonString;
import static com.cargowhale.docker.test.matchers.MediaTypeMatcher.hasMediaType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class BaseIntegrationTest {

    private static final String DOCKER_API_RAML_FILE = "docker/docker-api.raml";
    private static final String CARGO_WHALE_API_RAML_FILE = "api/cargo-whale-api.raml";

    @Autowired
    private DockerRestTemplate dockerRestTemplate;

    @Autowired
    private CargoWhaleProperties properties;

    protected MockRestServiceServer server;

    private final RamlModelResult dockerRamlModelResult;
    private final RamlModelResult cargoWhaleRamlModelResult;
    private Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>>> dockerResourceMap;
    private Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>>> cargoWhaleResourceMap;

    public BaseIntegrationTest() {
        this.dockerRamlModelResult = new RamlModelBuilder().buildApi(DOCKER_API_RAML_FILE);
        this.cargoWhaleRamlModelResult = new RamlModelBuilder().buildApi(CARGO_WHALE_API_RAML_FILE);
        this.dockerResourceMap = new HashMap<>();
    }

    @Before
    public void setUp() throws Exception {
        this.server = MockRestServiceServer.createServer(this.dockerRestTemplate);
    }

    @PostConstruct
    public void init() {
        verifyRamlModelResult(this.dockerRamlModelResult, DOCKER_API_RAML_FILE);
        verifyRamlModelResult(this.cargoWhaleRamlModelResult, CARGO_WHALE_API_RAML_FILE);

        this.dockerResourceMap = flattenRaml(this.dockerRamlModelResult.getApiV10());
        this.cargoWhaleResourceMap = flattenRaml(this.cargoWhaleRamlModelResult.getApiV10());
    }

    protected void expectDockerRequest(final MockRestServiceServer mockServer, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        String example = RamlApiVerifier.verifySetupAndReturnExample(DOCKER_API_RAML_FILE, path, method, status, mediaType, this.dockerResourceMap);
        mockServer.expect(requestTo(this.properties.getDockerUri() + path)).andRespond(withStatus(status).contentType(mediaType).body(example));
    }

    protected void verifyResponse(final ResponseEntity<String> response, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        String example = RamlApiVerifier.verifySetupAndReturnExample(CARGO_WHALE_API_RAML_FILE, path, method, status, mediaType, this.cargoWhaleResourceMap);

        assertThat(response.getStatusCode(), is(status));
        assertThat(response, hasMediaType(mediaType));
        assertThat(response.getBody(), equalToJsonString(example));
    }
}
