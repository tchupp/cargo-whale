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

    @Mock
    private EventsMapper eventsMapper;

    @InjectMocks
    private EventsClient client;

    @Test
    public void getAllEvents_ReturnsObservableEvents_NoParams() throws Exception {
        Event domainEvent1 = mock(Event.class);
        Event domainEvent2 = mock(Event.class);

        com.spotify.docker.client.messages.Event spotifyEvent1 = mock(com.spotify.docker.client.messages.Event.class);
        com.spotify.docker.client.messages.Event spotifyEvent2 = mock(com.spotify.docker.client.messages.Event.class);

        when(this.restTemplate.getForEventStream("/v1.26/events?since=0")).thenReturn(Flowable.just(spotifyEvent1, spotifyEvent2));
        when(this.eventsMapper.toEvent(spotifyEvent1)).thenReturn(domainEvent1);
        when(this.eventsMapper.toEvent(spotifyEvent2)).thenReturn(domainEvent2);

        this.client.getAllEvents()
            .test()
            .assertResult(domainEvent1, domainEvent2);
    }

    @Test
    public void getNewEvents_ReturnsObservableEvents_NoParams() throws Exception {
        Event domainEvent1 = mock(Event.class);
        Event domainEvent2 = mock(Event.class);

        com.spotify.docker.client.messages.Event spotifyEvent1 = mock(com.spotify.docker.client.messages.Event.class);
        com.spotify.docker.client.messages.Event spotifyEvent2 = mock(com.spotify.docker.client.messages.Event.class);

        when(this.restTemplate.getForEventStream("/v1.26/events")).thenReturn(Flowable.just(spotifyEvent1, spotifyEvent2));
        when(this.eventsMapper.toEvent(spotifyEvent1)).thenReturn(domainEvent1);
        when(this.eventsMapper.toEvent(spotifyEvent2)).thenReturn(domainEvent2);

        this.client.getNewEvents()
            .test()
            .assertResult(domainEvent1, domainEvent2);
    }
}