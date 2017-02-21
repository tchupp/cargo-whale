package com.cargowhale.docker.container.info.logs;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

public class LogStompSessionHandler extends StompSessionHandlerAdapter{

    private SimpMessagingTemplate messagingSender;
    private String containerId;

    public LogStompSessionHandler(SimpMessagingTemplate template, String containerId){
        this.messagingSender = template;
        this.containerId = containerId;
    }

    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        session.subscribe(connectedHeaders, new LogStompFrameHandler(this.messagingSender, containerId));
    }
}
