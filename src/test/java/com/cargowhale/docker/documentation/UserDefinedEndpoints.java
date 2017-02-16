package com.cargowhale.docker.documentation;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;

class UserDefinedEndpoints {

    private final LinkedMultiValueMap<String, HttpMethod> endpoints = new LinkedMultiValueMap<>();

    @JsonAnySetter
    void addMapping(final String key, final Object ignored) {
        if (key.contains("/api")) {
            String strippedKey = key.substring(1, key.length() - 1);
            String[] mappingGroups = strippedKey.split(",");
            String pathGroup = mappingGroups[0];
            String methodGroup = mappingGroups[1];

            String methodsPattern = "methods=[";

            String path = pathGroup.substring(1, pathGroup.length() - 1);
            for (final String it : methodGroup.substring(methodsPattern.length(), methodGroup.length() - 1).split(",")) {
                this.endpoints.add(path, HttpMethod.valueOf(it));
            }
        }
    }

    LinkedMultiValueMap<String, HttpMethod> getEndpoints() {
        return this.endpoints;
    }
}
