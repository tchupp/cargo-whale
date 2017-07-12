package com.cargowhale.docker.events;

import com.cargowhale.docker.rx.RequestAttributesAwareSingleObserver;
import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.cargowhale.docker.events.Event.Type.CONTAINER;

@RestController
public class EventsController {

    private final EventsService eventsService;
    private final EventsResourceProcessor resourceProcessor;

    @Autowired
    public EventsController(final EventsService eventsService, final EventsResourceProcessor resourceProcessor) {
        this.eventsService = eventsService;
        this.resourceProcessor = resourceProcessor;
    }

    @GetMapping(path = "/api/events")
    public Single<EventsResource> getPastEvents() {
        return this.eventsService
            .getPastEvents()
            .toList()
            .map(EventsResource::new)
            .lift((SingleOperator<EventsResource, EventsResource>) RequestAttributesAwareSingleObserver::build)
            .map(this.resourceProcessor::processPastEvents);
    }

    @GetMapping(path = "/api/events", params = {"follow=true"})
    public ObservableSseEmitter<Event> followEvents() {
        Observable<Event> eventObservable = this.eventsService
            .getNewEvents()
            .toObservable();

        return new ObservableSseEmitter<>(eventObservable);
    }

    @GetMapping(path = "/api/events/containers")
    public Single<EventsResource> getPastContainerEvents() {
        return this.eventsService
            .getPastEventsByType(CONTAINER)
            .toList()
            .map(EventsResource::new)
            .lift((SingleOperator<EventsResource, EventsResource>) RequestAttributesAwareSingleObserver::build)
            .map(this.resourceProcessor::processPastContainerEvents);
    }

    @GetMapping(path = "/api/events/containers", params = {"follow=true"})
    public ObservableSseEmitter<Event> followContainerEvents() {
        Observable<Event> eventObservable = this.eventsService
            .getNewEventsByType(CONTAINER)
            .toObservable();

        return new ObservableSseEmitter<>(eventObservable);
    }
}
