package com.cargowhale.docker.security

import groovy.transform.Canonical

import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Canonical
class UserCredentials {

    @NotNull
    @Size(min = 4, max = 50)
    String username

    @NotNull
    @Size(min = 4, max = 100)
    String password


}
