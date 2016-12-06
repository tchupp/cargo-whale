package com.cargowhale.docker.container.info

import com.cargowhale.docker.container.ContainerState
import com.cargowhale.docker.container.info.model.ContainerIndex
import com.cargowhale.docker.container.info.model.ContainerSummary
import org.springframework.stereotype.Component

@Component
class ContainerIndexBuilder {

    ContainerIndex buildContainerIndex(final List<ContainerSummary> containerSummaryList) {
        Map<ContainerState, Integer> stateMetadata = buildStateMetadata(containerSummaryList)

        return new ContainerIndex(containerSummaryList, stateMetadata)
    }

    private Map<ContainerState, Integer> buildStateMetadata(final List<ContainerSummary> containerSummaryList) {
        Map<ContainerState, Integer> containerStateSummary = [
                (ContainerState.CREATED)   : 0,
                (ContainerState.RESTARTING): 0,
                (ContainerState.RUNNING)   : 0,
                (ContainerState.PAUSED)    : 0,
                (ContainerState.EXITED)    : 0,
                (ContainerState.DEAD)      : 0
        ]

        containerSummaryList.each { containerSummary -> containerStateSummary[containerSummary.state]++ }

        return containerStateSummary
    }
}
