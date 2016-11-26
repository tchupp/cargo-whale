package com.cargowhale.docker.container

import groovy.transform.Canonical

@Canonical
public class LogFilters {

    String follow
    String stdout
    String stderr
    String since
    String timestamps
    String tail

    LogFilters(
            String follow = '0',
            String stdout = '1',
            String stderr = '1',
            String since = '0',
            String timestamps = '1',
            String tail = '100') {
        this.follow = follow
        this.stdout = stdout
        this.stderr = stderr
        this.since = since
        this.timestamps = timestamps
        this.tail = tail
    }
}
