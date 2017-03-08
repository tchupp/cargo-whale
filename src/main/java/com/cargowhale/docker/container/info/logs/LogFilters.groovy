package com.cargowhale.docker.container.info.logs

import com.cargowhale.docker.client.core.QueryParameters
import groovy.transform.Canonical
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap

import java.lang.reflect.Field

@Canonical
class LogFilters implements QueryParameters {

    Boolean follow = false
    Boolean stdout = false
    Boolean stderr = false
    Boolean timestamps = false
    Integer since = 0
    String tail = '100'

    @Override
    MultiValueMap<String, String> asQueryParameters() {
        MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>()
        List<Field> nonSyntheticFields = this.class.declaredFields.findAll { !it.synthetic }

        nonSyntheticFields.each { it ->
            queryParameters.add(it.name, this."$it.name" as String)
        }

        return queryParameters
    }

    @Override
    Map<String, String> asMap() {
        Map<String, String> queryParameters = new HashMap<>()
        List<Field> nonSyntheticFields = this.class.declaredFields.findAll { !it.synthetic }

        nonSyntheticFields.each { it ->
            queryParameters.put(it.name, this."$it.name" as String)
        }

        return queryParameters
    }
}
