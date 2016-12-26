package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerMemoryStats {

    Long maxUsage
    Long usage
    Long failCount
    Long limit

    @JsonProperty("max_usage")
    void setMaxUsage(Long maxUsage){this.maxUsage = maxUsage}

    @JsonProperty("maxUsage")
    Long getMaxUsage(){return this.maxUsage}

    @JsonProperty("usage")
    void setUsage(Long usage){this.usage = usage}

    @JsonProperty("usage")
    Long getUsage(){return this.usage}

    @JsonProperty("failcnt")
    void setFailCount(Long failCount){this.failCount = failCount}

    @JsonProperty("failCount")
    Long getFailCount(){return this.failCount}

    @JsonProperty("limit")
    void setLimit(Long limit){this.limit = limit}

    @JsonProperty("limit")
    Long getLimit(){return this.limit}
}
