package com.cargowhale.docker.container.info.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode
@ToString
class ContainerProcess {

    final String uid;
    final String pid;
    final String ppid;
    final String c;
    final String sTime;
    final String tty;
    final String time;
    final String cmd;

    ContainerProcess(List<String> process) {
        this.uid = process.get(0);
        this.pid = process.get(1);
        this.ppid = process.get(2);
        this.c = process.get(3);
        this.sTime = process.get(4);
        this.tty = process.get(5);
        this.time = process.get(6);
        this.cmd = process.get(7);
    }
}
