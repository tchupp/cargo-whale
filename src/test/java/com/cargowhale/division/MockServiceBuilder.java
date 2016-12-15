package com.cargowhale.division;

import com.cargowhale.division.raml.RamlSpec;
import com.cargowhale.division.raml.RamlSpecBuilder;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

public class MockServiceBuilder {

    private final MockRestServiceServer server;
    private final RamlSpec ramlSpec;
    private final String baseUrl;

    private MockServiceBuilder(final MockRestServiceServer server, final RamlSpec ramlSpec, final String baseUrl) {
        this.server = server;
        this.ramlSpec = ramlSpec;
        this.baseUrl = baseUrl;
    }

    public static MockServiceBuilder fromRestTemplate(final RestTemplate restTemplate, final String ramlSpecFilePath, final String baseUrl) {
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(ramlSpecFilePath);
        RamlSpec ramlSpec = RamlSpecBuilder.fromRamlApi10(ramlModelResult, ramlSpecFilePath);

        return new MockServiceBuilder(MockRestServiceServer.createServer(restTemplate), ramlSpec, baseUrl);
    }

    public void expectRequest(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        RequestMatcher requestTo = requestTo(this.baseUrl + path);
        String example = this.ramlSpec.findExample(path, method, status, mediaType);

        this.server.expect(requestTo).andRespond(withStatus(status).contentType(mediaType).body(example));
    }
}
