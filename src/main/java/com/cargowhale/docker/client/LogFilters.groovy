package com.cargowhale.docker.client

import groovy.transform.Canonical

@Canonical
class LogFilters {

    boolean details
    boolean follow
    boolean stdout
    boolean stderr
    boolean timestamps
    String since
    String tail

    LogFilters(
            boolean details = false,
            boolean follow = false,
            boolean stdout = true,
            boolean stderr = true,
            boolean timestamps = true,
            String since = '0',
            String tail = '100') {
        this.details = details
        this.follow = follow
        this.stdout = stdout
        this.stderr = stderr
        this.timestamps = timestamps
        this.since = since
        this.tail = tail
    }
}
