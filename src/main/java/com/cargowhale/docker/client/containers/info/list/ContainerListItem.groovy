package com.cargowhale.docker.client.containers.info.list

import com.cargowhale.docker.client.containers.ContainerState
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@Canonical
class ContainerListItem {

    @JsonProperty("State")
    ContainerState state

    @JsonProperty(value = "Id", access = WRITE_ONLY)
    String id

    @JsonProperty("Image")
    String image

    @JsonProperty("ImageID")
    String imageId

    @JsonProperty("Names")
    String[] names

    @JsonProperty("Status")
    String status
}
