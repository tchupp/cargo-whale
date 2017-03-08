package com.cargowhale.docker.container.info.logs;

import com.spotify.docker.client.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/containers")
public class ContainerLogsController {

    private final ContainerLogsClient logsClient;

    @Autowired
    public ContainerLogsController(final ContainerLogsClient logsClient) {
        this.logsClient = logsClient;
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        params = "stdout=1",
        produces = MediaType.TEXT_PLAIN_VALUE)
    public LogStream getStdOutLogs(@PathVariable final String id, @RequestParam final boolean stdout) {
        LogFilters logFilters = new LogFilters();
        logFilters.setStdout(stdout);

        return this.logsClient.getContainerLogStream(id, logFilters);
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        params = "stderr=1",
        produces = MediaType.TEXT_PLAIN_VALUE)
    public LogStream getStdErrLogs(@PathVariable final String id, @RequestParam final boolean stderr) {
        LogFilters logFilters = new LogFilters();
        logFilters.setStderr(stderr);

        return this.logsClient.getContainerLogStream(id, logFilters);
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        produces = MediaType.TEXT_PLAIN_VALUE)
    public LogStream getAllLogs(@PathVariable final String id) {
        LogFilters logFilters = new LogFilters();
        logFilters.setStdout(true);
        logFilters.setStderr(true);

        return this.logsClient.getContainerLogStream(id, logFilters);
    }
}
