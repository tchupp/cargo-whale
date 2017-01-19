package com.cargowhale.docker.container.info.stats

import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport

@Canonical
class ContainerStatsResource extends ResourceSupport {

    Date read
    Map<String, NetworkStats> networks
    MemoryStats memoryStats
    CpuStats cpuStats
    CpuStats precpuStats

    @Canonical
    static class NetworkStats {

        Long rxBytes
        Long rxPackets
        Long rxDropped
        Long rxErrors
        Long txBytes
        Long txPackets
        Long txDropped
        Long txErrors
    }

    @Canonical
    static class MemoryStats {

        Stats stats
        Long maxUsage
        Long usage
        Long failCount
        Long limit

        @Canonical
        static class Stats {

            Long totalMajorPageFault
            Long cache
            Long mappedFile
            Long totalInactiveFile
            Long pgpgout
            Long rss
            Long totalMappedFile
            Long writeback
            Long unevictable
            Long pgpgin
            Long totalUnevictable
            Long majorPageFault
            Long totalRss
            Long totalRssHuge
            Long totalWriteback
            Long totalInactiveAnon
            Long rssHuge
            BigInteger hierarchicalMemoryLimit
            Long totalPageFault
            Long totalActiveFile
            Long activeAnon
            Long totalActiveAnon
            Long totalPgpgout
            Long totalCache
            Long inactiveAnon
            Long activeFile
            Long pageFault
            Long inactiveFile
            Long totalPgpgin
        }
    }

    @Canonical
    static class CpuStats {

        CpuUsage cpuUsage
        Long systemCpuUsage
        ThrottlingData throttlingData

        @Canonical
        static class CpuUsage {

            Long totalUsage
            List<Long> percpuUsage
            Long usageInKernelmode
            Long usageInUsermode
        }

        @Canonical
        static class ThrottlingData {

            Long periods
            Long throttledPeriods
            Long throttledTime
        }
    }
}
