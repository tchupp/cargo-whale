package com.cargowhale.docker.security

import groovy.transform.Canonical

@Canonical
class JWTToken {

    final String token

    JWTToken(final String token) {
        this.token = token
    }
}
