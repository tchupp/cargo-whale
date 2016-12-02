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

    ContainerProcess(String uid, String pid, String ppid, String c, String sTime, String tty, String time, String cmd) {
        this.uid = uid;
        this.pid = pid;
        this.ppid = ppid;
        this.c = c;
        this.sTime = sTime;
        this.tty = tty;
        this.time = time;
        this.cmd = cmd;
    }
}
