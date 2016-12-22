package com.cargowhale.docker.client.containers.info.stats

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.Canonical

@Canonical
class ContainerStats {

    String datetimeRead
    Map<String, Long> pidStats
    Map<String, ContainerNetworkStats> networkStats
    ContainerMemoryStats memoryStats
    ContainerCpuStats cpuStats
    ContainerCpuStats preCpuStats

    @JsonProperty("read")
    public void setDatetimeRead(String datetime){this.datetimeRead = datetime;}

    @JsonProperty("datetimeRead")
    public String getDatetimeRead(){return this.datetimeRead;}

    @JsonProperty("pids_stats")
    public void setPidStats(Map<String, Long> pidStats){this.pidStats = pidStats;}

    @JsonProperty("pidStats")
    public Map<String, Long> getPidStats(){return this.pidStats;}

    @JsonProperty("networks")
    public void setNetworkStats(Map<String, ContainerNetworkStats> networkStats){this.networkStats = networkStats;}

    @JsonProperty("networkStats")
    public Map<String, ContainerNetworkStats> getNetworkStats(){return this.networkStats;}

    @JsonProperty("memory_stats")
    public void setMemoryStats(ContainerMemoryStats memoryStats){this.memoryStats = memoryStats;}

    @JsonProperty("memoryStats")
    public ContainerMemoryStats getMemoryStats(){return this.memoryStats;}

    @JsonProperty("cpu_stats")
    public void setCpuStats(ContainerCpuStats cpuStats){this.cpuStats = cpuStats;}

    @JsonProperty("cpuStats")
    public ContainerCpuStats getCpuStats(){return this.cpuStats;}

    @JsonProperty("precpu_stats")
    public void setPreCpuStats(ContainerCpuStats preCpuStats){this.preCpuStats = preCpuStats;}

    @JsonProperty("preCpuStats")
    public ContainerCpuStats getPreCpuStats(){return this.preCpuStats;}

}
