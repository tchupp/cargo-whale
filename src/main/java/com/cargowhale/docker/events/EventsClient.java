package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import io.reactivex.Flowable;
import org.springframework.stereotype.Repository;

@Repository
public class EventsClient {

    private static final String EVENTS_ENDPOINT = "/v1.26/events?since=0";
    private static final String NEW_EVENTS_ENDPOINT = "/v1.26/events";

    private final DockerRestTemplate restTemplate;
    private final EventsMapper eventsMapper;

    public EventsClient(final DockerRestTemplate restTemplate, final EventsMapper eventsMapper) {
        this.restTemplate = restTemplate;
        this.eventsMapper = eventsMapper;
    }

    Flowable<Event> getAllEvents() {
        return this.restTemplate.getForEventStream(EVENTS_ENDPOINT)
            .map(this.eventsMapper::toEvent)
            .doOnNext(System.out::println);
    }

    Flowable<Event> getNewEvents() {
        return this.restTemplate.getForEventStream(NEW_EVENTS_ENDPOINT)
            .map(this.eventsMapper::toEvent)
            .doOnNext(System.out::println);
    }
}
