package com.cargowhale.division.raml;

import com.cargowhale.division.raml.model.*;
import groovy.json.StringEscapeUtils;
import org.raml.v2.api.RamlModelResult;
import org.raml.v2.api.model.v10.api.Api;
import org.raml.v2.api.model.v10.bodies.Response;
import org.raml.v2.api.model.v10.datamodel.TypeDeclaration;
import org.raml.v2.api.model.v10.methods.Method;
import org.raml.v2.api.model.v10.resources.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static com.cargowhale.division.raml.RamlApiVerifier.verifyRamlModelResult;

public abstract class RamlSpecBuilder {

    public static RamlSpec fromRamlApi10(final RamlModelResult modelResult, final String ramlSpecFile) {
        verifyRamlModelResult(modelResult, ramlSpecFile);

        Map<String, RamlHttpMethods> ramlResources = new HashMap<>();

        for (final Resource resource : flattenResources(modelResult.getApiV10())) {
            ramlResources.put(resource.resourcePath(), buildMethodMap(resource));
        }

        return new RamlSpec(ramlResources, ramlSpecFile);
    }

    private static Queue<Resource> flattenResources(final Api api) {
        final Queue<Resource> queue = new LinkedList<>();

        for (final Resource resource : api.resources()) {
            addResources(queue, resource);
        }
        return queue;
    }

    private static void addResources(final Queue<Resource> queue, final Resource parent) {
        queue.add(parent);

        parent.resources().forEach((Resource child) -> addResources(queue, child));
    }

    private static RamlHttpMethods buildMethodMap(final Resource resource) {
        final RamlHttpMethods methodMap = new RamlHttpMethods();

        for (final Method method : resource.methods()) {
            String methodName = method.method().toUpperCase();
            methodMap.put(HttpMethod.resolve(methodName), buildStatusMap(method));
        }

        return methodMap;
    }

    private static RamlResponses buildStatusMap(final Method method) {
        final RamlResponses responseMap = new RamlResponses();

        for (final Response response : method.responses()) {
            Integer statusCode = Integer.valueOf(response.code().value());
            responseMap.put(HttpStatus.valueOf(statusCode), buildMediaTypeMap(response));
        }

        return responseMap;
    }

    private static RamlMediaTypes buildMediaTypeMap(final Response response) {
        final RamlMediaTypes mediaTypeMap = new RamlMediaTypes();

        for (final TypeDeclaration mediaType : response.body()) {
            mediaTypeMap.put(MediaType.valueOf(mediaType.name()), buildExampleMap(mediaType));
        }

        return mediaTypeMap;
    }

    private static RamlExamples buildExampleMap(final TypeDeclaration mediaType) {
        RamlExamples examplesMap = new RamlExamples();

        if (mediaType.examples().isEmpty()) {
            examplesMap.put("default", StringEscapeUtils.unescapeJava(mediaType.example().value()));
        } else {
            mediaType.examples().forEach(example -> examplesMap.put(example.name(), StringEscapeUtils.unescapeJava(example.value())));
        }

        return examplesMap;
    }
}
