package com.cargowhale.docker.client.core

import org.springframework.util.MultiValueMap

interface QueryParameters {

    MultiValueMap<String, String> asQueryParameters()
}