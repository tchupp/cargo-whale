package com.cargowhale.division;

import com.cargowhale.division.raml.RamlSpecBuilder;
import com.cargowhale.division.raml.model.RamlSpec;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.RequestMatcher;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.io.UnsupportedEncodingException;

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
        MockRestServiceServer server = MockRestServiceServer.createServer(restTemplate);
        RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(ramlSpecFilePath);
        RamlSpec ramlSpec = RamlSpecBuilder.fromRamlApi10(ramlModelResult, ramlSpecFilePath);

        return new MockServiceBuilder(server, ramlSpec, baseUrl);
    }

    public void expectRequest(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) throws UnsupportedEncodingException {
        String example = this.ramlSpec.findExample(path, method, status, mediaType);
        RequestMatcher requestTo = requestTo(this.baseUrl + UriUtils.encodeQuery(path, "UTF-8"));

        this.server.expect(requestTo).andRespond(withStatus(status).contentType(mediaType).body(example));
    }

    public void reset() {
        this.server.reset();
    }
}
