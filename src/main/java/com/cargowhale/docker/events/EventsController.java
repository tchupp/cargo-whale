package com.cargowhale.docker.events;

import com.cargowhale.docker.rx.RequestAttributesAwareSingleObserver;
import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import static com.cargowhale.docker.events.Event.Type.CONTAINER;

@RestController
public class EventsController {

    private final EventsRepository eventsRepository;
    private final EventsResourceProcessor resourceProcessor;

    @Autowired
    public EventsController(final EventsRepository eventsRepository, final EventsResourceProcessor resourceProcessor) {
        this.eventsRepository = eventsRepository;
        this.resourceProcessor = resourceProcessor;
    }

    @GetMapping(path = "/api/events")
    public Single<EventsResource> getPastEvents() {
        return this.eventsRepository
            .getAllEvents()
            .take(5, TimeUnit.MILLISECONDS)
            .toList()
            .map(EventsResource::new)
            .lift((SingleOperator<EventsResource, EventsResource>) RequestAttributesAwareSingleObserver::build)
            .map(this.resourceProcessor::processPastEvents);
    }

    @GetMapping(path = "/api/events", params = {"follow=true"})
    public ObservableSseEmitter<Event> followEvents() {
        Observable<Event> eventObservable = this.eventsRepository
            .getAllEvents()
            .toObservable();

        return new ObservableSseEmitter<>(60000L, MediaType.APPLICATION_JSON, eventObservable);
    }

    @GetMapping(path = "/api/events/containers")
    public Single<EventsResource> getPastContainerEvents() {
        return this.eventsRepository
            .getEventsByType(CONTAINER)
            .take(5, TimeUnit.MILLISECONDS)
            .toList()
            .map(EventsResource::new)
            .lift((SingleOperator<EventsResource, EventsResource>) RequestAttributesAwareSingleObserver::build)
            .map(this.resourceProcessor::processPastContainerEvents);
    }

    @GetMapping(path = "/api/events/containers", params = {"follow=true"})
    public ObservableSseEmitter<Event> followContainerEvents() {
        Observable<Event> eventObservable = this.eventsRepository
            .getEventsByType(CONTAINER)
            .toObservable();

        return new ObservableSseEmitter<>(60000L, MediaType.APPLICATION_JSON, eventObservable);
    }
}
