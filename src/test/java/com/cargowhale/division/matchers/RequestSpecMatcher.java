package com.cargowhale.division.matchers;

import com.cargowhale.division.raml.RamlSpec;
import com.cargowhale.division.raml.RamlSpecBuilder;
import org.raml.v2.api.RamlModelBuilder;
import org.raml.v2.api.RamlModelResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.ResultMatcher;

import static com.cargowhale.division.matchers.JsonMatcher.equalToJsonString;
import static com.cargowhale.division.matchers.MediaTypeMatcher.isCompatibleMediaType;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class RequestSpecMatcher {

    public static RequestSpecMatcher responseIsInSpec(final String ramlSpecFile) {
        final RamlModelResult ramlModelResult = new RamlModelBuilder().buildApi(ramlSpecFile);
        final RamlSpec ramlSpec = RamlSpecBuilder.fromRamlApi10(ramlModelResult, ramlSpecFile);

        return new RequestSpecMatcher(ramlSpec);
    }

    private final RamlSpec ramlSpec;

    private RequestSpecMatcher(final RamlSpec ramlSpec) {
        this.ramlSpec = ramlSpec;
    }

    public ResultMatcher with(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        final String example = this.ramlSpec.findExample(path, method, status, mediaType);

        return buildResultMatcher(status, mediaType, example);
    }

    private ResultMatcher buildResultMatcher(final HttpStatus status, final MediaType mediaType, final String example) {
        return result -> {
            MockHttpServletResponse response = result.getResponse();

            assertThat(response.getStatus(), is(status.value()));
            assertThat(response.getContentType(), isCompatibleMediaType(mediaType));
            assertThat(response.getContentAsString(), equalToJsonString(example));
        };
    }
}
