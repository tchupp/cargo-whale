package com.cargowhale.docker.container.info.top

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.EqualsAndHashCode
import groovy.transform.Immutable

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@Immutable
class ContainerProcessIndex {

    @JsonProperty(access = WRITE_ONLY)
    final String id
    final List<Map<String, String>> processes
}
