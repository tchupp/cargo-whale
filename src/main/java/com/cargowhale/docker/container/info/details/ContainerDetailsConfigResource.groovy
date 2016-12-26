package com.cargowhale.docker.container.info.details

import com.cargowhale.docker.client.containers.info.inspect.ContainerDetailsConfig
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerDetailsConfigResource {

    @JsonProperty("cmd")
    String[] command

    @JsonProperty("env")
    String[] environment

    ContainerDetailsConfigResource(ContainerDetailsConfig entity) {
        this.command = entity.command
        this.environment = entity.environment
    }
}
