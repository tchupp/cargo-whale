package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.container.info.resource.ContainerState;
import com.cargowhale.docker.container.info.resource.ListContainersClient;
import com.cargowhale.docker.container.info.resource.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class LogWebsocketScheduler {

    private final SimpMessagingTemplate messageSender;
    private final ListContainersClient listContainersClient;

    @Autowired
    public LogWebsocketScheduler(SimpMessagingTemplate messageSender, ListContainersClient listContainersClient) {
        this.messageSender = messageSender;
        this.listContainersClient = listContainersClient;
    }

    private List<Container> getRunningContainers(){
        return this.listContainersClient.listContainers(ListContainersParam.state(ContainerState.RUNNING));
    }

    @Scheduled(fixedRate = 1000)
    public void reportCurrentTime() {
        List<Container> containers = getRunningContainers();
        for (Container container : containers) {
            messageSender.convertAndSend(String.format("/test/topic/%s", container.id()), container.id() + ": " + new Date().getTime());
        }
    }
}
