package com.cargowhale.docker.events;

import io.jmnarloch.spring.boot.rxjava.async.ObservableSseEmitter;
import io.reactivex.Observable;
import io.reactivex.Single;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.cargowhale.docker.events.Event.Type.CONTAINER;

@RestController
public class EventsController {

    private final EventsRepository eventsRepository;

    @Autowired
    public EventsController(final EventsRepository eventsRepository) {
        this.eventsRepository = eventsRepository;
    }

    @GetMapping(path = "/api/events")
    public Single<List<Event>> getPastEvents() {
        return this.eventsRepository
            .getAllEvents()
            .take(5, TimeUnit.MILLISECONDS)
            .toList();
    }

    @GetMapping(path = "/api/events", params = {"follow=true"})
    public ObservableSseEmitter<Event> followEvents() {
        Observable<Event> eventObservable = this.eventsRepository
            .getAllEvents()
            .toObservable();

        return new ObservableSseEmitter<>(60000L, MediaType.APPLICATION_JSON, eventObservable);
    }

    @GetMapping(path = "/api/events/container")
    public Single<List<Event>> getPastContainerEvents() {
        return this.eventsRepository
            .getEventsByType(CONTAINER)
            .take(5, TimeUnit.MILLISECONDS)
            .toList();
    }

    @GetMapping(path = "/api/events/container", params = {"follow=true"})
    public ObservableSseEmitter<Event> followContainerEvents() {
        Observable<Event> eventObservable = this.eventsRepository
            .getEventsByType(CONTAINER)
            .toObservable();

        return new ObservableSseEmitter<>(60000L, MediaType.APPLICATION_JSON, eventObservable);
    }
}
