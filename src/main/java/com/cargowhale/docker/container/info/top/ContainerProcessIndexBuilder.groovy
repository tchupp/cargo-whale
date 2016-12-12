package com.cargowhale.docker.container.info.top

import com.cargowhale.docker.client.containers.info.top.ContainerTop
import org.springframework.stereotype.Component

@Component
class ContainerProcessIndexBuilder {

    ContainerProcessIndex buildProcessIndex(String containerId, ContainerTop topResponse) {
        List<Map<String, String>> processes = topResponse.processes.collect { process ->
            buildProcess(topResponse.titles, process)
        }

        return new ContainerProcessIndex(containerId, processes)
    }

    private static Map<String, String> buildProcess(List<String> titles, List<String> attributes) {
        List<List<String>> tuples = [titles, attributes].transpose()
        return tuples.collectEntries { it }
    }
}
