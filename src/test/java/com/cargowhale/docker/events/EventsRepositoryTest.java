package com.cargowhale.docker.events;

import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class EventsRepositoryTest {

    private EventsRepository eventsRepository;

    @Before
    public void setUp() throws Exception {
        this.eventsRepository = new EventsRepository();
    }

    @Test
    public void getAllEvents_ReceivesAllAddedEvents() throws Exception {
        Event event1 = new Event(Event.Type.DAEMON);
        Event event2 = new Event(Event.Type.CONTAINER);
        Event event3 = new Event(Event.Type.IMAGE);

        this.eventsRepository.addEvent(event1);
        this.eventsRepository.addEvent(event2);

        TestSubscriber<Event> testSubscriber = this.eventsRepository.getAllEvents().test();

        testSubscriber
            .assertResult(event1, event2);

        this.eventsRepository.addEvent(event3);

        testSubscriber = this.eventsRepository.getAllEvents().test();

        testSubscriber
            .assertResult(event1, event2, event3);
    }

    @Test
    public void getNewEvents_ReturnsStreamOfEventsAddedAfterSubscription() throws Exception {
        Event event1 = new Event(Event.Type.DAEMON);
        Event event2 = new Event(Event.Type.CONTAINER);
        Event event3 = new Event(Event.Type.IMAGE);

        this.eventsRepository.addEvent(event1);

        TestSubscriber<Event> testSubscriber = this.eventsRepository.getNewEvents().test();

        testSubscriber
            .assertSubscribed()
            .assertNoErrors()
            .assertNotComplete()
            .assertValues();

        this.eventsRepository.addEvent(event2);

        testSubscriber
            .assertNotComplete()
            .assertValues(event2);

        this.eventsRepository.addEvent(event3);

        testSubscriber
            .assertNotComplete()
            .assertValues(event2, event3);
    }
}