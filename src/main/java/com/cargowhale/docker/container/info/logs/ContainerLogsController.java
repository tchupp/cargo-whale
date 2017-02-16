package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.client.core.DockerEndpointBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@RestController
@RequestMapping("/api/containers")
public class ContainerLogsController {

    private final SimpMessagingTemplate messageSender;
    private final DockerEndpointBuilder endpointBuilder;
    private final ContainerLogsService logsService;
    private final ContainerLogsResourceAssembler logsResourceAssembler;

    @Autowired
    public ContainerLogsController(final ContainerLogsService logsService, final ContainerLogsResourceAssembler logsResourceAssembler, final SimpMessagingTemplate messageSender, final DockerEndpointBuilder endpointBuilder) {
        this.logsService = logsService;
        this.logsResourceAssembler = logsResourceAssembler;
        this.messageSender = messageSender;
        this.endpointBuilder = endpointBuilder;
    }

    @RequestMapping(value = "/{id}/logs",
        method = RequestMethod.GET,
        produces = MediaTypes.HAL_JSON_VALUE)
    public ContainerLogsResource getContainerLogsById(@PathVariable final String id, final LogFilters logFilters) {
        ContainerLogs containerLogs = this.logsService.getContainerLogsById(id, logFilters);
        return this.logsResourceAssembler.toResource(containerLogs);
    }

    @MessageMapping("/hello")
    public void greeting(String message) throws Exception {
        System.out.println("Hello?");
        messageSender.convertAndSend("/topic/api/containers/hello", message);
    }

//    @SubscribeMapping("/{id}/logs")
//    public void getTailedLogs(@PathVariable final String id){
//        System.out.println("HANDLING");
//        WebSocketClient webSocketClient = new StandardWebSocketClient();
//        WebSocketStompClient stompClient = new WebSocketStompClient(webSocketClient);
//        stompClient.setMessageConverter(new MappingJackson2MessageConverter());
////        stompClient.setTaskScheduler(taskScheduler); // for heartbeats
//
//        String dockerLogsUrl = endpointBuilder.getContainerLogsEndpoint(id);
//        //String url = "ws://127.0.0.1:8080/endpoint";
//        String url = dockerLogsUrl + "/stream=1";
//        StompSessionHandler sessionHandler = new LogStompSessionHandler(messageSender, id);
//        stompClient.connect(url, sessionHandler);
//
//        String DESTINATION = "/%s/logs";
//        messageSender.convertAndSend(String.format(DESTINATION, id), "WELCOME TO " +id);
//
//    }
}
