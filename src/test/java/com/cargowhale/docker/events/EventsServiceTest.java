package com.cargowhale.docker.events;

import io.reactivex.Flowable;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.Date;
import java.time.Instant;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EventsServiceTest {

    private EventsService eventsService;

    @Mock
    private EventsClient eventsClient;

    @Mock
    private EventsRepository eventsRepository;

    @Before
    public void setUp() throws Exception {
        this.eventsService = new EventsService(this.eventsRepository, Schedulers.trampoline());
    }

    @Test
    public void getPastEvents() throws Exception {
        Instant now = Instant.now();
        Instant pastTime = now.minusSeconds(1);

        Event pastEvent1 = new Event(Event.Type.DAEMON, "", null, Date.from(pastTime));
        Event pastEvent2 = new Event(Event.Type.CONTAINER, "", null, Date.from(pastTime));

        when(this.eventsRepository.getAllEvents()).thenReturn(Flowable.fromArray(pastEvent1, pastEvent2));

        this.eventsService.getPastEvents()
            .test()
            .assertResult(pastEvent1, pastEvent2);
    }

    @Test
    public void getNewEvents() throws Exception {
        PublishProcessor<Event> publisher = PublishProcessor.create();

        Instant now = Instant.now();
        Instant futureTime = now.plusSeconds(1);

        Event newEvent1 = new Event(Event.Type.IMAGE, "", null, Date.from(futureTime));
        Event newEvent2 = new Event(Event.Type.NETWORK, "", null, Date.from(futureTime));

        when(this.eventsRepository.getNewEvents()).thenReturn(Flowable.fromPublisher(publisher));

        TestSubscriber<Event> testSubscriber = this.eventsService.getNewEvents().test();

        publisher.onNext(newEvent1);

        testSubscriber
            .assertSubscribed()
            .assertValues(newEvent1)
            .assertNoErrors();

        publisher.onNext(newEvent2);

        testSubscriber
            .assertValues(newEvent1, newEvent2)
            .assertNoErrors();
    }

    @Test
    public void getPastEventsByType() throws Exception {
        Instant now = Instant.now();
        Instant pastTime = now.minusSeconds(1);
        Instant futureTime = now.plusSeconds(1);

        Event.Type correctEventType = Event.Type.CONTAINER;

        Event pastEvent1 = new Event(correctEventType, "", null, Date.from(pastTime));
        Event pastEvent2 = new Event(Event.Type.IMAGE, "", null, Date.from(futureTime));

        when(this.eventsRepository.getAllEvents()).thenReturn(Flowable.fromArray(pastEvent1, pastEvent2));

        this.eventsService.getPastEventsByType(correctEventType)
            .test()
            .assertResult(pastEvent1);
    }

    @Test
    public void getNewEventsByType() throws Exception {
        PublishProcessor<Event> publisher = PublishProcessor.create();

        Instant now = Instant.now();
        Instant pastTime = now.minusSeconds(1);
        Instant futureTime = now.plusSeconds(1);

        Event.Type correctEventType = Event.Type.CONTAINER;

        Event newEvent1 = new Event(Event.Type.IMAGE, "", null, Date.from(pastTime));
        Event newEvent2 = new Event(correctEventType, "", null, Date.from(futureTime));

        when(this.eventsRepository.getNewEvents()).thenReturn(Flowable.fromPublisher(publisher));

        TestSubscriber<Event> testSubscriber = this.eventsService.getNewEventsByType(correctEventType).test();

        publisher.onNext(newEvent1);

        testSubscriber
            .assertSubscribed()
            .assertValues()
            .assertNoErrors();

        publisher.onNext(newEvent2);

        testSubscriber
            .assertValues(newEvent2)
            .assertNoErrors();
    }
}