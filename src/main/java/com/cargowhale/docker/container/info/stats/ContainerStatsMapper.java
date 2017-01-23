package com.cargowhale.docker.container.info.stats;

import com.spotify.docker.client.messages.ContainerStats;
import com.spotify.docker.client.messages.CpuStats;
import com.spotify.docker.client.messages.MemoryStats;
import com.spotify.docker.client.messages.NetworkStats;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.Map;

/**
 * https://docs.docker.com/engine/admin/runmetrics/
 */

@Mapper(componentModel = "spring")
public interface ContainerStatsMapper {

    /**
     * CONTAINER NETWORK STATS
     */

    @Mappings({
        @Mapping(target = "rxBytes", expression = "java(stats.rxBytes())"),
        @Mapping(target = "rxPackets", expression = "java(stats.rxPackets())"),
        @Mapping(target = "rxDropped", expression = "java(stats.rxDropped())"),
        @Mapping(target = "rxErrors", expression = "java(stats.rxErrors())"),
        @Mapping(target = "txBytes", expression = "java(stats.txBytes())"),
        @Mapping(target = "txPackets", expression = "java(stats.txPackets())"),
        @Mapping(target = "txDropped", expression = "java(stats.txDropped())"),
        @Mapping(target = "txErrors", expression = "java(stats.txErrors())"),
    })
    ContainerStatsResource.NetworkStats toNetworkStats(NetworkStats stats);

    Map<String, ContainerStatsResource.NetworkStats> toNetworkStatsMap(Map<String, NetworkStats> statsMap);


    /**
     * CONTAINER MEMORY STATS STATS
     */

    @Mappings({
        @Mapping(target = "totalMajorPageFault", expression = "java(stats.totalPgmajfault())"),
        @Mapping(target = "cache", expression = "java(stats.cache())"),
        @Mapping(target = "mappedFile", expression = "java(stats.mappedFile())"),
        @Mapping(target = "totalInactiveFile", expression = "java(stats.totalInactiveFile())"),
        @Mapping(target = "pgpgout", expression = "java(stats.pgpgout())"),
        @Mapping(target = "rss", expression = "java(stats.rss())"),
        @Mapping(target = "totalMappedFile", expression = "java(stats.totalMappedFile())"),
        @Mapping(target = "writeback", expression = "java(stats.writeback())"),
        @Mapping(target = "unevictable", expression = "java(stats.unevictable())"),
        @Mapping(target = "pgpgin", expression = "java(stats.pgpgin())"),
        @Mapping(target = "totalUnevictable", expression = "java(stats.totalUnevictable())"),
        @Mapping(target = "majorPageFault", expression = "java(stats.pgmajfault())"),
        @Mapping(target = "totalRss", expression = "java(stats.totalRss())"),
        @Mapping(target = "totalRssHuge", expression = "java(stats.totalRssHuge())"),
        @Mapping(target = "totalWriteback", expression = "java(stats.totalWriteback())"),
        @Mapping(target = "totalInactiveAnon", expression = "java(stats.totalInactiveAnon())"),
        @Mapping(target = "rssHuge", expression = "java(stats.rssHuge())"),
        @Mapping(target = "hierarchicalMemoryLimit", expression = "java(stats.hierarchicalMemoryLimit())"),
        @Mapping(target = "totalPageFault", expression = "java(stats.totalPgfault())"),
        @Mapping(target = "totalActiveFile", expression = "java(stats.totalActiveFile())"),
        @Mapping(target = "activeAnon", expression = "java(stats.activeAnon())"),
        @Mapping(target = "totalActiveAnon", expression = "java(stats.totalActiveAnon())"),
        @Mapping(target = "totalPgpgout", expression = "java(stats.totalPgpgout())"),
        @Mapping(target = "totalCache", expression = "java(stats.totalCache())"),
        @Mapping(target = "inactiveAnon", expression = "java(stats.inactiveAnon())"),
        @Mapping(target = "activeFile", expression = "java(stats.activeFile())"),
        @Mapping(target = "pageFault", expression = "java(stats.pgfault())"),
        @Mapping(target = "inactiveFile", expression = "java(stats.inactiveFile())"),
        @Mapping(target = "totalPgpgin", expression = "java(stats.totalPgpgin())")
    })
    ContainerStatsResource.MemoryStats.Stats toMemoryStatsStats(MemoryStats.Stats stats);

    /**
     * CONTAINER MEMORY STATS
     */

    @Mappings({
        @Mapping(target = "stats", expression = "java(toMemoryStatsStats(stats.stats()))"),
        @Mapping(target = "maxUsage", expression = "java(stats.maxUsage())"),
        @Mapping(target = "usage", expression = "java(stats.usage())"),
        @Mapping(target = "failCount", expression = "java(stats.failcnt())"),
        @Mapping(target = "limit", expression = "java(stats.limit())")
    })
    ContainerStatsResource.MemoryStats toMemoryStats(MemoryStats stats);


    /**
     * CONTAINER CPU USAGE
     */

    @Mappings({
        @Mapping(target = "totalUsage", expression = "java(usage.totalUsage())"),
        @Mapping(target = "percpuUsage", expression = "java(usage.percpuUsage())"),
        @Mapping(target = "usageInKernelmode", expression = "java(usage.usageInKernelmode())"),
        @Mapping(target = "usageInUsermode", expression = "java(usage.usageInUsermode())"),
    })
    ContainerStatsResource.CpuStats.CpuUsage toCpuUsage(CpuStats.CpuUsage usage);

    /**
     * CONTAINER THROTTLING DATA
     */

    @Mappings({
        @Mapping(target = "periods", expression = "java(data.periods())"),
        @Mapping(target = "throttledPeriods", expression = "java(data.throttledPeriods())"),
        @Mapping(target = "throttledTime", expression = "java(data.throttledTime())")
    })
    ContainerStatsResource.CpuStats.ThrottlingData toThrottlingData(CpuStats.ThrottlingData data);

    /**
     * CONTAINER CPU STATS
     */

    @Mappings({
        @Mapping(target = "cpuUsage", expression = "java(toCpuUsage(stats.cpuUsage()))"),
        @Mapping(target = "systemCpuUsage", expression = "java(stats.systemCpuUsage())"),
        @Mapping(target = "throttlingData", expression = "java(toThrottlingData(stats.throttlingData()))")
    })
    ContainerStatsResource.CpuStats toCpuStats(CpuStats stats);


    /**
     * CONTAINER STATS RESOURCE
     */

    @Mappings({
        @Mapping(target = "read", expression = "java(stats.read())"),
        @Mapping(target = "networks", expression = "java(toNetworkStatsMap(stats.networks()))"),
        @Mapping(target = "memoryStats", expression = "java(toMemoryStats(stats.memoryStats()))"),
        @Mapping(target = "cpuStats", expression = "java(toCpuStats(stats.cpuStats()))"),
        @Mapping(target = "precpuStats", expression = "java(toCpuStats(stats.precpuStats()))")
    })
    ContainerStatsResource toResource(ContainerStats stats, String containerId);
}
