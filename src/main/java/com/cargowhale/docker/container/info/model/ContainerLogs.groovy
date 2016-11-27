package com.cargowhale.docker.container.info.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
public class ContainerLogs {

    final String logs;

    ContainerLogs(final String logs) {
        this.logs = logs
    }
}
