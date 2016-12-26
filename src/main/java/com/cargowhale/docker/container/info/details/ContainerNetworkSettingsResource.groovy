package com.cargowhale.docker.container.info.details

import com.cargowhale.docker.client.containers.info.inspect.ContainerNetworkSettings
import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.Canonical

@JsonInclude(JsonInclude.Include.NON_NULL)
@Canonical
class ContainerNetworkSettingsResource {

    Map<String, Object> ports

    ContainerNetworkSettingsResource(ContainerNetworkSettings entity) {
        this.ports = entity.ports
    }
}
