package com.cargowhale.docker.events;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EventsRepositoryTest {

    @Test
    public void getAllEvents_ReceivesAddedEvent_One() throws Exception {
        Event event = mock(Event.class);

        EventsRepository eventsRepository = new EventsRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventsRepository.getAllEvents().test();

        eventsRepository.addEvent(event);

        testSubscriber
            .assertSubscribed()
            .assertValues(event)
            .assertNoErrors();
    }

    @Test
    public void getAllEvents_ReceivesAllAddedEvents_Many() throws Exception {
        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);

        EventsRepository eventsRepository = new EventsRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventsRepository.getAllEvents().test();

        eventsRepository.addEvent(event1);
        eventsRepository.addEvent(event2);

        testSubscriber
            .assertSubscribed()
            .assertValues(event1, event2)
            .assertNoErrors();
    }

    @Test
    public void getEventsByType_ReturnsOnlyContainerEvents() throws Exception {
        Event.Type correctEventType = Event.Type.CONTAINER;

        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);

        when(event1.getType()).thenReturn(correctEventType);
        when(event2.getType()).thenReturn(Event.Type.IMAGE);

        EventsRepository eventsRepository = new EventsRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventsRepository.getEventsByType(correctEventType).test();

        eventsRepository.addEvent(event1);
        eventsRepository.addEvent(event2);

        testSubscriber
            .assertSubscribed()
            .assertValues(event1)
            .assertNoErrors();
    }

    @Test
    public void getEventsById_ByIdReturnsOnlySpecificEvents() throws Exception {
        String correctActorId = "CORRECT";

        Event event1 = mock(Event.class);
        Event event2 = mock(Event.class);
        Event event3 = mock(Event.class);

        Event.Actor actor1 = mock(Event.Actor.class);
        Event.Actor actor2 = mock(Event.Actor.class);
        Event.Actor actor3 = mock(Event.Actor.class);

        when(event1.getType()).thenReturn(Event.Type.CONTAINER);
        when(event2.getType()).thenReturn(Event.Type.CONTAINER);
        when(event3.getType()).thenReturn(Event.Type.IMAGE);

        when(event1.getActor()).thenReturn(actor1);
        when(event2.getActor()).thenReturn(actor2);
        when(event3.getActor()).thenReturn(actor3);

        when(actor1.getId()).thenReturn(correctActorId);
        when(actor2.getId()).thenReturn("INCORRECT");
        when(actor3.getId()).thenReturn(correctActorId);

        EventsRepository eventsRepository = new EventsRepository(Schedulers.trampoline());

        TestSubscriber<Event> testSubscriber = eventsRepository.getEventsById(correctActorId).test();

        eventsRepository.addEvent(event1);
        eventsRepository.addEvent(event2);
        eventsRepository.addEvent(event3);

        testSubscriber
            .assertSubscribed()
            .assertValues(event1, event3)
            .assertNoErrors();
    }
}