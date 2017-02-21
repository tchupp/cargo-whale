package com.cargowhale.docker.container.info.logs;

import com.spotify.docker.client.LogStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/containers")
public class ContainerLogsController {

    private final ContainerLogsClient logsClient;
    private final SimpMessagingTemplate messageSender;

    @Autowired
    public ContainerLogsController(final ContainerLogsClient logsClient, final SimpMessagingTemplate messageSender) {
        this.logsClient = logsClient;
        this.messageSender = messageSender;
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

    @MessageMapping("/hello")
    public void greeting(final String message) throws Exception {
        System.out.println("Hello?");
        this.messageSender.convertAndSend("/topic/api/containers/hello", message);
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
