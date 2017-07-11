package com.cargowhale.docker.events;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

public class EventsService {

    private final EventsRepository eventsRepository;
    private final Scheduler scheduler;

    public EventsService(final EventsRepository eventsRepository, final Scheduler scheduler) {
        this.eventsRepository = eventsRepository;
        this.scheduler = scheduler;
    }

    Flowable<Event> getPastEvents() {
        return this.eventsRepository.getAllEvents();
    }

    Flowable<Event> getNewEvents() {
        return this.eventsRepository.getNewEvents()
            .subscribeOn(this.scheduler);
    }

    Flowable<Event> getPastEventsByType(final Event.Type type) {
        return this.eventsRepository.getAllEvents()
            .filter(event -> event.getType().equals(type));
    }

    Flowable<Event> getNewEventsByType(final Event.Type type) {
        return this.eventsRepository.getNewEvents()
            .subscribeOn(this.scheduler)
            .filter(event -> event.getType().equals(type));
    }
}
