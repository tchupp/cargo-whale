package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.info.list.ContainerPort
import groovy.transform.Canonical

@Canonical
class ContainerPortResource {

    String ip
    Integer privatePort
    Integer publicPort
    String type

    ContainerPortResource(ContainerPort entity) {
        this.ip = entity.ip
        this.privatePort = entity.privatePort
        this.publicPort = entity.publicPort
        this.type = entity.type
    }
}
