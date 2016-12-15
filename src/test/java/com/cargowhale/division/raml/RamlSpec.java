package com.cargowhale.division.raml;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Map;

public class RamlSpec {

    private final Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>>> ramlResources;
    private final String ramlSpecFile;

    RamlSpec(final Map<String, Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>>> ramlResources, final String ramlSpecFile) {
        this.ramlResources = ramlResources;
        this.ramlSpecFile = ramlSpecFile;
    }

    public String findExample(final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>> methods = findMethods(path);
        Map<HttpStatus, Map<MediaType, String>> responses = findResponses(methods, path, method);
        Map<MediaType, String> mediaTypes = findMediaTypes(responses, path, method, status);

        return findExample(mediaTypes, path, method, status, mediaType);
    }

    private Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>> findMethods(final String path) {
        Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>> methods = this.ramlResources.get(path);

        if (methods == null) {
            throw new IllegalArgumentException(String.format("%1s %2s", formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile)));
        }

        return methods;
    }

    private Map<HttpStatus, Map<MediaType, String>> findResponses(final Map<HttpMethod, Map<HttpStatus, Map<MediaType, String>>> methods, final String path, final HttpMethod method) {
        Map<HttpStatus, Map<MediaType, String>> responses = methods.get(method);

        if (responses == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s %3s", formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile)));
        }

        return responses;
    }

    private Map<MediaType, String> findMediaTypes(final Map<HttpStatus, Map<MediaType, String>> responses, final String path, final HttpMethod method, final HttpStatus status) {
        Map<MediaType, String> mediaTypes = responses.get(status);

        if (mediaTypes == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s and %3s %4s", formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile)));
        }

        return mediaTypes;
    }

    private String findExample(final Map<MediaType, String> mediaTypes, final String path, final HttpMethod method, final HttpStatus status, final MediaType mediaType) {
        String example = mediaTypes.get(mediaType);

        if (example == null) {
            throw new IllegalArgumentException(String.format("%1s for: %2s, %3s and %4s %5s", formatMediaType(mediaType), formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(this.ramlSpecFile)));
        }

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

    private static String formatNotSetupForFileMessage(final String ramlSpecFile) {
        return String.format("is not setup for '%s'", ramlSpecFile);
    }
}
