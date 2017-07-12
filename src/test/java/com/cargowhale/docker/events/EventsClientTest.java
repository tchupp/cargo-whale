package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import io.reactivex.Flowable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventsClientTest {

    @Mock
    private DockerRestTemplate restTemplate;

    @InjectMocks
    private EventsClient client;

    @Test
    public void getAllEvents_ReturnsStreamOfEvents() throws Exception {
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);

        when(this.restTemplate.getForEventStream("/v1.26/events?since=0")).thenReturn(Flowable.just(event1, event2));

        this.client.getAllEvents()
            .test()
            .assertResult(event1, event2);
    }
}