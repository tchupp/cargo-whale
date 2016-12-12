package com.cargowhale.docker.container.management

import groovy.transform.Canonical

@Canonical
class ChangeStateResponse {

    String name

    ChangeStateResponse(String name) {
        this.name = name
    }
}
