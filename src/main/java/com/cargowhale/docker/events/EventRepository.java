package com.cargowhale.docker.events;

import com.spotify.docker.client.messages.Event;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.ReplayProcessor;

class EventRepository {

    private final FlowableProcessor<Event> eventReplayProcessor;
    private final Scheduler scheduler;

    EventRepository(final Scheduler scheduler) {
        this.scheduler = scheduler;
        this.eventReplayProcessor = ReplayProcessor.create();
    }

    Flowable<Event> getAllEvents() {
        return this.eventReplayProcessor.subscribeOn(this.scheduler);
    }

    void addEvent(final Event event) {
        this.eventReplayProcessor.onNext(event);
    }
}
