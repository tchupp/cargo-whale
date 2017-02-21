package com.cargowhale.docker.container.info.logs;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class LogStompFrameHandler implements StompFrameHandler {

    public static final String DESTINATION = "/%s/logs";
    private SimpMessagingTemplate messageSender;
    private String containerId;

    public LogStompFrameHandler(SimpMessagingTemplate template, String containerId){
        this.messageSender = template;
        this.containerId = containerId;
    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        ContainerLogs logs = (ContainerLogs) payload;
        messageSender.convertAndSend(String.format(DESTINATION, this.containerId), logs);
    }
}
