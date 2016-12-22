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
    public void setMaxUsage(Long maxUsage){this.maxUsage = maxUsage;}

    @JsonProperty("maxUsage")
    public Long getMaxUsage(){return this.maxUsage;}

    @JsonProperty("usage")
    public void setUsage(Long usage){this.usage = usage;}

    @JsonProperty("usage")
    public Long getUsage(){return this.usage;}

    @JsonProperty("failcnt")
    public void setFailCount(Long failCount){this.failCount = failCount;}

    @JsonProperty("failCount")
    public Long getFailCount(){return this.failCount;}

    @JsonProperty("limit")
    public void setLimit(Long limit){this.limit = limit;}

    @JsonProperty("limit")
    public Long getLimit(){return this.limit;}
}
