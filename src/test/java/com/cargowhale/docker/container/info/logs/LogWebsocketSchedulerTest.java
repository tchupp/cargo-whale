package com.cargowhale.docker.container.info.logs;

import com.cargowhale.docker.container.info.resource.ContainerState;
import com.cargowhale.docker.container.info.resource.ListContainersClient;
import com.cargowhale.docker.container.info.resource.ListContainersParam;
import com.spotify.docker.client.messages.Container;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LogWebsocketSchedulerTest {

    @Mock
    private SimpMessagingTemplate messageSender;

    @Mock
    private ListContainersClient listContainersClient;

    @InjectMocks
    private LogWebsocketScheduler scheduler;

    @Test
    public void scheduledTaskDoesNothingWhenNoRunningContainers() {
        when(this.listContainersClient.listContainers(ListContainersParam.state(ContainerState.RUNNING)))
            .thenReturn(newArrayList());
        this.scheduler.reportCurrentTime();
        verify(this.messageSender, never()).convertAndSend(anyString(), anyLong());
    }

    @Test
    public void scheduledTaskSendsLogForOneRunningContainer() {
        Container container = mock(Container.class);
        when(this.listContainersClient.listContainers(ListContainersParam.state(ContainerState.RUNNING)))
            .thenReturn(newArrayList(container));
        when(container.id()).thenReturn("hello!");
        this.scheduler.reportCurrentTime();
        verify(this.messageSender, times(1)).convertAndSend(eq("/test/topic/hello!"), anyLong());
    }

    @Test
    public void scheduledTaskSendsLogsForManyRunningContainers(){
        Container container1 = mock(Container.class);
        Container container2 = mock(Container.class);
        when(this.listContainersClient.listContainers(ListContainersParam.state(ContainerState.RUNNING)))
            .thenReturn(newArrayList(container1, container2));
        when(container1.id()).thenReturn("hello1!");
        when(container2.id()).thenReturn("hello2!");
        this.scheduler.reportCurrentTime();
        verify(this.messageSender, times(1)).convertAndSend(eq("/test/topic/hello1!"), anyLong());
        verify(this.messageSender, times(1)).convertAndSend(eq("/test/topic/hello2!"), anyLong());
    }


}
