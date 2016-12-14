package com.cargowhale.docker.test.integration;

import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.common.ValidationResult;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

import static java.lang.System.out;

final class RamlApiVerifier {

    private RamlApiVerifier() {
    }

    static String verifySetupAndReturnExample(final String ramlApiFile, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType, final Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>>> resourceMap) {
        String pathMessage = String.format("Path '%s'", path);
        String methodMessage = String.format("Method '%s'", method);
        String statusMessage = String.format("Status '%s'", status);
        String mediaTypeMessage = String.format("MediaType '%s'", mediaType);
        String isNotSetupMessage = String.format("is not setup for '%s'", ramlApiFile);

        Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>> methodMap = resourceMap.get(path);
        if (methodMap == null) {
            throw new IllegalArgumentException(String.format("%1s %2s", pathMessage, isNotSetupMessage));
        }

        Map<HttpStatus, Map<MediaType, String>> responseMap = methodMap.get(method);
        if (responseMap == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s %3s", methodMessage, pathMessage, isNotSetupMessage));
        }

        Map<MediaType, String> mediaTypeMap = responseMap.get(status);
        if (mediaTypeMap == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s and %3s %4s", statusMessage, methodMessage, pathMessage, isNotSetupMessage));
        }

        String example = mediaTypeMap.get(mediaType);
        if (example == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s, %3s and %4s %5s", mediaTypeMessage, statusMessage, methodMessage, pathMessage, isNotSetupMessage));
        }

        return example;
    }

    static void verifyRamlModelResult(final RamlModelResult ramlModelResult, final String ramlFile) {
        if (ramlModelResult.hasErrors()) {
            for (final ValidationResult result : ramlModelResult.getValidationResults()) {
                out.println(result.getMessage());
            }
            throw new IllegalStateException("Errors in " + ramlFile);
        }
    }
}
