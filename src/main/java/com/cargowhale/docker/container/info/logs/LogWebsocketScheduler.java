package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.container.info.resource.ContainerState;
import com.cargowhale.docker.container.info.resource.ListContainersClient;
import com.cargowhale.docker.container.info.resource.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@ConditionalOnProperty(prefix = "cargowhale.docker", name = "enable-log-tailing", matchIfMissing = false)
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
    public void reportCurrentTime() throws InterruptedException {
            List<Container> containers = getRunningContainers();
            for (Container container : containers) {
                retrieveAndSendLogs(container.id());
            }
    }

    @Async
    public void retrieveAndSendLogs(String containerId) throws InterruptedException {
        System.out.println(Thread.currentThread().getId());
        messageSender.convertAndSend(String.format("/test/topic/%s", containerId), containerId + ": " + new Date().getTime());
    }
}
