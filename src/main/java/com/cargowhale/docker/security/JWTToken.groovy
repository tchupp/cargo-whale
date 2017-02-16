package com.cargowhale.docker.security

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class JWTToken {

    final String token

    @JsonCreator
    JWTToken(@JsonProperty("token") final String token) {
        this.token = token
    }
}
