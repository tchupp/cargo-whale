package com.cargowhale.division.raml;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.Set;

public abstract class RamlSpecErrorMessageFormatter {

    public static String formatRamlSpecErrorMessage(final HttpMethod method, final String path, final String ramlSpecFile, final Set<HttpMethod> keySet) {
        return String.format("%1s for: %2s %3s. Options are: %4s", formatMethod(method), formatPath(path), formatNotSetupForFileMessage(ramlSpecFile), keySet);
    }

    public static String formatRamlSpecErrorMessage(final HttpStatus status, final HttpMethod method, final String path, final String ramlSpecFile, final Set<HttpStatus> keySet) {
        return String.format("%1s for: %2s and %3s %4s. Options are: %5s", formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(ramlSpecFile), keySet);
    }

    public static String formatRamlSpecErrorMessage(final MediaType mediaType, final HttpStatus status, final HttpMethod method, final String path, final String ramlSpecFile, final Set<String> keySet) {
        return String.format("%1s for: %2s, %3s and %4s %5s. Options are: %6s", formatMediaType(mediaType), formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(ramlSpecFile), keySet);
    }

    public static String formatRamlSpecErrorMessage(final String exampleName, final MediaType mediaType, final HttpStatus status, final HttpMethod method, final String path, final String ramlSpecFile, final Set<String> keySet) {
        return String.format("%1s for: %2s, %3s, %4s and %5s %6s. Options are: %7s", formatExampleName(exampleName), formatMediaType(mediaType), formatStatus(status), formatMethod(method), formatPath(path), formatNotSetupForFileMessage(ramlSpecFile), keySet);
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

    public static String formatRamlSpecErrorMessage(final String path, final String ramlSpecFile, final Set<String> keySet) {
        return String.format("%1s %2s. Options are: %3s", formatPath(path), formatNotSetupForFileMessage(ramlSpecFile), keySet);
    }
}
