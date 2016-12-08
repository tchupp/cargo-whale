package com.cargowhale.docker.container.info

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import com.cargowhale.docker.container.info.model.ContainerIndex
import org.springframework.stereotype.Component

@Component
class ContainerIndexBuilder {

    ContainerIndex buildContainerIndex(final List<ContainerListItem> containerList) {
        Map<ContainerState, Integer> stateMetadata = buildStateMetadata(containerList)

        return new ContainerIndex(containerList, stateMetadata)
    }

    private Map<ContainerState, Integer> buildStateMetadata(
            final List<ContainerListItem> containerSummaryList) {
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
