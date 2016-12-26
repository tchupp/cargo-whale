package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical
import groovy.transform.EqualsAndHashCode

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY

@EqualsAndHashCode(excludes = "id")
@Canonical
class ContainerStats {

    @JsonProperty(access = WRITE_ONLY)
    String id

    String datetimeRead
    Map<String, Long> pidStats
    Map<String, ContainerNetworkStats> networkStats
    ContainerMemoryStats memoryStats
    ContainerCpuStats cpuStats
    ContainerCpuStats preCpuStats

    void setId(String id){this.id = id}

    @JsonProperty("read")
    void setDatetimeRead(String datetime){this.datetimeRead = datetime}

    @JsonProperty("datetimeRead")
    String getDatetimeRead(){return this.datetimeRead}

    @JsonProperty("pids_stats")
    void setPidStats(Map<String, Long> pidStats){this.pidStats = pidStats}

    @JsonProperty("pidStats")
    Map<String, Long> getPidStats(){return this.pidStats}

    @JsonProperty("networks")
    void setNetworkStats(Map<String, ContainerNetworkStats> networkStats){this.networkStats = networkStats}

    @JsonProperty("networkStats")
    Map<String, ContainerNetworkStats> getNetworkStats(){return this.networkStats}

    @JsonProperty("memory_stats")
    void setMemoryStats(ContainerMemoryStats memoryStats){this.memoryStats = memoryStats}

    @JsonProperty("memoryStats")
    ContainerMemoryStats getMemoryStats(){return this.memoryStats}

    @JsonProperty("cpu_stats")
    void setCpuStats(ContainerCpuStats cpuStats){this.cpuStats = cpuStats}

    @JsonProperty("cpuStats")
    ContainerCpuStats getCpuStats(){return this.cpuStats}

    @JsonProperty("precpu_stats")
    void setPreCpuStats(ContainerCpuStats preCpuStats){this.preCpuStats = preCpuStats}

    @JsonProperty("preCpuStats")
    ContainerCpuStats getPreCpuStats(){return this.preCpuStats}

}
