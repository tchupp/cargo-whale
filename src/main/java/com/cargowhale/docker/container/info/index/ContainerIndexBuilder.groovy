package com.cargowhale.docker.container.info.index

import com.cargowhale.docker.client.containers.ContainerState
import com.spotify.docker.client.messages.Container
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class ContainerIndexBuilder {

    private final ContainerMapper mapper

    @Autowired
    ContainerIndexBuilder(final ContainerMapper mapper) {
        this.mapper = mapper
    }

    ContainerIndexResource buildContainerIndex(final List<Container> containers) {
        Map<ContainerState, Integer> stateMetadata = buildStateMetadata(containers)

        return new ContainerIndexResource(this.mapper.toResources(containers), stateMetadata)
    }

    private Map<ContainerState, Integer> buildStateMetadata(final List<Container> containers) {
        Map<ContainerState, Integer> states = [
            (ContainerState.ALL)       : 0,
            (ContainerState.CREATED)   : 0,
            (ContainerState.RESTARTING): 0,
            (ContainerState.RUNNING)   : 0,
            (ContainerState.PAUSED)    : 0,
            (ContainerState.EXITED)    : 0,
            (ContainerState.DEAD)      : 0
        ]

        containers.each { container ->
            states[ContainerState.from(container.state())]++
            states[ContainerState.ALL]++
        }

        return states
    }
}
