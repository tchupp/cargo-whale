package com.cargowhale.docker.events;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;

class EventsRepository {

    private final Deque<Event> eventStorage;
    private final FlowableProcessor<Event> eventBus;

    EventsRepository() {
        this.eventStorage = new ConcurrentLinkedDeque<>();
        this.eventBus = PublishProcessor.create();
    }

    Flowable<Event> getAllEvents() {
        return Flowable.fromIterable(this.eventStorage);
    }

    Flowable<Event> getNewEvents() {
        return this.eventBus;
    }

    void addEvent(final Event event) {
        this.eventStorage.add(event);
        this.eventBus.onNext(event);
    }
}
