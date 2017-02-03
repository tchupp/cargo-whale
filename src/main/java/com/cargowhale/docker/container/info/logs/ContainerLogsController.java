package com.cargowhale.docker.container.info.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/containers")
public class ContainerLogsController {

    private SimpMessagingTemplate messageSender;
    private final ContainerLogsService logsService;
    private final ContainerLogsResourceAssembler logsResourceAssembler;

    @Autowired
    public ContainerLogsController(final ContainerLogsService logsService, final ContainerLogsResourceAssembler logsResourceAssembler, SimpMessagingTemplate messageSender) {
        this.logsService = logsService;
        this.logsResourceAssembler = logsResourceAssembler;
        this.messageSender = messageSender;
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerLogsResource getContainerLogsById(@PathVariable final String id, final LogFilters logFilters) {
        ContainerLogs containerLogs = this.logsService.getContainerLogsById(id, logFilters);
        return this.logsResourceAssembler.toResource(containerLogs);
    }

    @MessageMapping("/hello")
    public void greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        messageSender.convertAndSend("/topic/" + message.getRoom(), new Greeting("Hello, " + message.getName() + "!"));
    }
}
