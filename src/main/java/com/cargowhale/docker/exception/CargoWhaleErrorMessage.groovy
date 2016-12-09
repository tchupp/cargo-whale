package com.cargowhale.docker.exception

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class CargoWhaleErrorMessage {

    final String path
    final String message
    final String error

    public CargoWhaleErrorMessage(final String path, final String message, final String error) {
        this.path = path
        this.message = message
        this.error = error
    }
}
