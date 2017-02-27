package com.cargowhale.docker.container.info.logs;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class LogWebsocketSchedulerTest {

    @Mock
    private SimpMessagingTemplate messageSender;

    @InjectMocks
    private LogWebsocketScheduler scheduler;

    @Test
    public void scheduledTaskSendsCurrentTimestampToTopic(){
        this.scheduler.reportCurrentTime();
        verify(this.messageSender).convertAndSend(eq("/test/topic/hello"), anyLong());
    }
}
