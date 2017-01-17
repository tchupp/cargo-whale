package com.cargowhale.docker.container.info.top

import com.spotify.docker.client.messages.TopResults
import org.springframework.stereotype.Component

@Component
class ContainerProcessIndexBuilder {

    ContainerProcessIndex buildProcessIndex(String containerId, TopResults results) {
        List<Map<String, String>> processes = results.processes().collect { process ->
            buildProcess(results.titles(), process)
        }

        return new ContainerProcessIndex(containerId, processes)
    }

    private static Map<String, String> buildProcess(List<String> titles, List<String> attributes) {
        List<List<String>> tuples = [titles, attributes].transpose()
        return tuples.collectEntries { it }
    }
}
