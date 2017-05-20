package com.cargowhale.docker.events;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.ReplayProcessor;

class EventsRepository {

    private final FlowableProcessor<Event> eventReplayProcessor;
    private final Scheduler scheduler;

    EventsRepository(final Scheduler scheduler) {
        this.scheduler = scheduler;
        this.eventReplayProcessor = ReplayProcessor.create();
    }

    Flowable<Event> getAllEvents() {
        return this.eventReplayProcessor.subscribeOn(this.scheduler);
    }

    Flowable<Event> getEventsByType(final Event.Type eventType) {
        return getAllEvents().filter((event) -> filterByType(event, eventType));
    }

    private boolean filterByType(final Event event, final Event.Type eventType) {
        return event.getType().equals(eventType);
    }

    Flowable<Event> getEventsById(final String id) {
        return getAllEvents().filter(event -> filterById(event, id));
    }

    private boolean filterById(final Event event, final String id) {
        return event.getActor().getId().equals(id);
    }

    void addEvent(final Event event) {
        this.eventReplayProcessor.onNext(event);
    }
}
