package com.cargowhale.division.raml;

import org.apache.commons.lang.Validate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

public class RamlSpec {

    private static final String DEFAULT_EXAMPLE_NAME = "default";

    private final Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>>> ramlResources;
    private final String ramlSpecFile;

    RamlSpec(final Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>>> ramlResources, final String ramlSpecFile) {
        this.ramlResources = ramlResources;
        this.ramlSpecFile = ramlSpecFile;
    }

    public String findExample(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        return findExample(path, method, status, mediaType, DEFAULT_EXAMPLE_NAME);
    }

    public String findExample(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType, final String exampleName) {
        Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>> methods = findMethods(path);
        Map<HttpStatus, Map<MediaType, Map<String, String>>> responses = findResponses(methods, path, method);
        Map<MediaType, Map<String, String>> mediaTypes = findMediaTypes(responses, path, method, status);

        return findExample(mediaTypes, path, method, status, mediaType, exampleName);
    }

    private Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>> findMethods(final String path) {
        Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>> methods = this.ramlResources.get(path);
        Validate.notNull(methods, String.format("%1s %2s. Options are: %3s", formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile), this.ramlResources.keySet()));

        return methods;
    }

    private Map<HttpStatus, Map<MediaType, Map<String, String>>> findResponses(final Map<HttpMethod, Map<HttpStatus, Map<MediaType, Map<String, String>>>> methods, final String path, final HttpMethod method) {
        Map<HttpStatus, Map<MediaType, Map<String, String>>> responses = methods.get(method);
        Validate.notNull(responses, String.format("%1s for: %2s %3s. Options are: %4s", formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile), methods.keySet()));

        return responses;
    }

    private Map<MediaType, Map<String, String>> findMediaTypes(final Map<HttpStatus, Map<MediaType, Map<String, String>>> responses, final String path, final HttpMethod method, final HttpStatus status) {
        Map<MediaType, Map<String, String>> mediaTypes = responses.get(status);
        Validate.notNull(mediaTypes, String.format("%1s for: %2s and %3s %4s. Options are: %7s", formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile), responses.keySet()));

        return mediaTypes;
    }

    private String findExample(final Map<MediaType, Map<String, String>> mediaTypes, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType, final String exampleName) {
        Map<String, String> exampleMap = mediaTypes.get(mediaType);
        Validate.notNull(exampleMap, String.format("%1s for: %2s, %3s and %4s %5s. Options are: %6s", formatMediaType(mediaType), formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile), mediaTypes.keySet()));

        String example = exampleMap.get(exampleName);
        Validate.notNull(example, String.format("%1s for: %2s, %3s, %4s and %5s %6s. Options are: %7s", formatExampleName(exampleName), formatMediaType(mediaType), formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile), exampleMap.keySet()));

        return example;
    }

    private static String formatPath(final String path) {
        return String.format("Path '%s'", path);
    }

    private static String formatMethod(final HttpMethod method) {
        return String.format("Method '%s'", method);
    }

    private static String formatStatus(final HttpStatus status) {
        return String.format("Status '%s'", status);
    }

    private static String formatMediaType(final MediaType mediaType) {
        return String.format("MediaType '%s'", mediaType);
    }

    private static String formatExampleName(final String exampleName) {
        return String.format("Example '%s'", exampleName);
    }

    private static String formatNotSetupForFileMessage(final String ramlSpecFile) {
        return String.format("is not setup for '%s'", ramlSpecFile);
    }
}
