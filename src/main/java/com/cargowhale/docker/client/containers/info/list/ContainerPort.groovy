package com.cargowhale.docker.client.containers.info.list

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerPort {

    @JsonProperty("IP")
    String ip

    @JsonProperty("PrivatePort")
    Integer privatePort

    @JsonProperty("PublicPort")
    Integer publicPort

    @JsonProperty("Type")
    String type
}
