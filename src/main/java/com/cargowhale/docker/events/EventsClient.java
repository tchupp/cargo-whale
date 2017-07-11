package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import io.reactivex.Flowable;
import org.springframework.stereotype.Repository;

@Repository
public class EventsClient {

    private static final String EVENTS_ENDPOINT = "/v1.26/events?since=0";

    private final DockerRestTemplate restTemplate;

    public EventsClient(final DockerRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    Flowable<Event> getAllEvents() {
        return this.restTemplate.getForEventStream(EVENTS_ENDPOINT);
    }
}
