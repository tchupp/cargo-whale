package com.cargowhale.docker.events;

import com.spotify.docker.client.messages.Event;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import static org.mockito.Mockito.mock;

public class EventRepositoryTest {

    @Test
    public void getAllEventsReceivesAddedEvent_One() throws Exception {
        Event event = mock(Event.class);

        EventRepository eventRepository = new EventRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventRepository.getAllEvents().test();

        eventRepository.addEvent(event);

        testSubscriber
            .assertSubscribed()
            .assertValues(event)
            .assertNoErrors();
    }

    @Test
    public void getAllEventsReceivesAddedEvents_Many() throws Exception {
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);

        EventRepository eventRepository = new EventRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventRepository.getAllEvents().test();

        eventRepository.addEvent(event1);
        eventRepository.addEvent(event2);

        testSubscriber
            .assertSubscribed()
            .assertValues(event1, event2)
            .assertNoErrors();
    }
}