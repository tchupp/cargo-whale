package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.Event;
import io.reactivex.Flowable;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class EventClientTest {

    @Mock
    private DockerRestTemplate restTemplate;

    @InjectMocks
    private EventClient client;

    @Test
    public void getEventsReturnsObservableEvents_NoParams() throws Exception {
        Flowable<Event> events = Flowable.just(mock(Event.class));

        when(this.restTemplate.getForEventStream("/v1.26/events?since=0")).thenReturn(events);

        Flowable<Event> actual = this.client.getEvents();

        assertThat(actual, is(events));
    }
}