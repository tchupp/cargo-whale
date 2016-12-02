package com.cargowhale.docker.container.info.model

import com.fasterxml.jackson.annotation.JsonProperty

class DockerContainerProcessIndex {

    List<String> titles;
    List<List<String>> processes;

    DockerContainerProcessIndex(@JsonProperty("Titles") final List<String> titles,
    @JsonProperty("Processes") final List<List<String>> processes) {
        this.titles = titles
        this.processes = processes
    }

}
