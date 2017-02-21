package com.cargowhale.docker.container.info.logs;

import com.spotify.docker.client.LogStream;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompFrameHandler;
import org.springframework.messaging.simp.stomp.StompHeaders;

import java.lang.reflect.Type;

public class LogStompFrameHandler implements StompFrameHandler {

    private static final String DESTINATION = "/%s/logs";
    private final SimpMessagingTemplate messageSender;
    private final String containerId;

    LogStompFrameHandler(final SimpMessagingTemplate template, final String containerId) {
        this.messageSender = template;
        this.containerId = containerId;
    }

    @Override
    public Type getPayloadType(final StompHeaders headers) {
        return null;
    }

    @Override
    public void handleFrame(final StompHeaders headers, final Object payload) {
        LogStream logs = (LogStream) payload;
        this.messageSender.convertAndSend(String.format(DESTINATION, this.containerId), logs);
    }
}
