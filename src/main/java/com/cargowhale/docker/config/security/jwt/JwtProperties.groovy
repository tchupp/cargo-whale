package com.cargowhale.docker.config.security.jwt

import groovy.transform.Canonical
import org.springframework.boot.context.properties.ConfigurationProperties

@Canonical
@ConfigurationProperties(prefix = "cargowhale.security.jwt", ignoreUnknownFields = false)
class JwtProperties {

    String secret
    long tokenValidityInSeconds = 1800
}
