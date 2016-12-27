package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import com.cargowhale.docker.client.containers.info.list.ContainerListItem
import org.springframework.stereotype.Component

@Component
class ContainerIndexBuilder {

    ContainerIndex buildContainerIndex(final List<ContainerListItem> containerList) {
        Map<ContainerState, Integer> stateMetadata = buildStateMetadata(containerList)

        return new ContainerIndex(containerList, stateMetadata)
    }

    private Map<ContainerState, Integer> buildStateMetadata(final List<ContainerListItem> containerSummaryList) {
        Map<ContainerState, Integer> containerStateSummary = [
            (ContainerState.ALL)       : 0,
            (ContainerState.CREATED)   : 0,
            (ContainerState.RESTARTING): 0,
            (ContainerState.RUNNING)   : 0,
            (ContainerState.PAUSED)    : 0,
            (ContainerState.EXITED)    : 0,
            (ContainerState.DEAD)      : 0
        ]

        containerSummaryList.each { containerSummary ->
            containerStateSummary[containerSummary.state]++
            containerStateSummary[ContainerState.ALL]++
        }


        return containerStateSummary
    }
}
