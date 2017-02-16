package com.cargowhale.division.raml.model;

import com.cargowhale.division.raml.RamlSpecErrorMessageFormatter;
import org.apache.commons.lang.Validate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

public class RamlSpec {

    private static final String DEFAULT_EXAMPLE_NAME = "default";

    private final Map<String, RamlHttpMethods> ramlResources;
    private final String ramlSpecFile;

    public RamlSpec(final Map<String, RamlHttpMethods> ramlResources, final String ramlSpecFile) {
        this.ramlResources = ramlResources;
        this.ramlSpecFile = ramlSpecFile;
    }

    private RamlHttpMethods findMethods(final String path) {
        RamlHttpMethods methods = this.ramlResources.get(path);
        Validate.notNull(methods, RamlSpecErrorMessageFormatter.formatRamlSpecErrorMessage(path, this.ramlSpecFile, this.ramlResources.keySet()));

        return methods;
    }

    public RamlResponses findResponses(final String path, final HttpMethod method) {
        RamlHttpMethods methods = findMethods(path);

        RamlResponses responses = methods.get(method);
        Validate.notNull(responses, RamlSpecErrorMessageFormatter.formatRamlSpecErrorMessage(method, path, this.ramlSpecFile, methods.keySet()));

        return responses;
    }

    private RamlMediaTypes findMediaTypes(final String path, final HttpMethod method, final HttpStatus status) {
        RamlResponses responses = findResponses(path, method);

        RamlMediaTypes mediaTypes = responses.get(status);
        Validate.notNull(mediaTypes, RamlSpecErrorMessageFormatter.formatRamlSpecErrorMessage(status, method, path, this.ramlSpecFile, responses.keySet()));

        return mediaTypes;
    }

    private RamlExamples findExamples(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        RamlMediaTypes mediaTypes = findMediaTypes(path, method, status);

        RamlExamples exampleMap = mediaTypes.get(mediaType);
        Validate.notNull(exampleMap, RamlSpecErrorMessageFormatter.formatRamlSpecErrorMessage(mediaType, status, method, path, this.ramlSpecFile, mediaTypes.keySet()));

        return exampleMap;
    }

    public String findExample(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        return findExample(path, method, status, mediaType, DEFAULT_EXAMPLE_NAME);
    }

    public String findExample(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType, final String exampleName) {
        RamlExamples exampleMap = findExamples(path, method, status, mediaType);

        String example = exampleMap.get(exampleName);
        Validate.notNull(example, RamlSpecErrorMessageFormatter.formatRamlSpecErrorMessage(exampleName, mediaType, status, method, path, this.ramlSpecFile, exampleMap.keySet()));

        return example;
    }
}
