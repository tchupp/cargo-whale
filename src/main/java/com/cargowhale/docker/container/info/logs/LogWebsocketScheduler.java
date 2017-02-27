package com.cargowhale.docker.container.info.logs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class LogWebsocketScheduler {

    private final SimpMessagingTemplate messageSender;

    @Autowired
    public LogWebsocketScheduler(final SimpMessagingTemplate messageSender) {
        this.messageSender = messageSender;
    }

    @Scheduled(fixedRate = 1)
    public void reportCurrentTime() {
        messageSender.convertAndSend("/test/topic/hello", new Date().getTime());
    }
}
