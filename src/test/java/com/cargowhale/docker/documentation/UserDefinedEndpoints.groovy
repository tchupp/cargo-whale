package com.cargowhale.docker.documentation

import com.fasterxml.jackson.annotation.JsonAnySetter
import groovy.transform.Canonical
import org.springframework.http.HttpMethod
import org.springframework.util.LinkedMultiValueMap

@Canonical
class UserDefinedEndpoints {

    LinkedMultiValueMap<String, HttpMethod> endpoints = new LinkedMultiValueMap<>()

    @JsonAnySetter
    void addMapping(String key, Object ignored) {
        if (key.contains("/api")) {
            String[] mappingGroups = key.split(',')
            def pathGroup = mappingGroups[0]
            def methodGroup = mappingGroups[1]

            def methodsPattern = 'methods=['

            String path = pathGroup.substring(2, pathGroup.length() - 1)
            String[] split = methodGroup.substring(methodsPattern.length(), methodGroup.length() - 1).split(',')
            split.each {
                this.endpoints.add(path, HttpMethod.valueOf(it))
            }
        }
    }
}
