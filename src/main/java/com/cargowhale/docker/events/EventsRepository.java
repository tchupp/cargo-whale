package com.cargowhale.docker.events;

import io.reactivex.Flowable;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

class EventsRepository {

    private final Deque<Event> eventStorage;

    EventsRepository() {
        this.eventStorage = new ConcurrentLinkedDeque<>();
    }

    Flowable<Event> getAllEvents() {
        return Flowable.fromIterable(this.eventStorage);
    }

    void addEvent(final Event event) {
        this.eventStorage.add(event);
    }
}
