package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventsClient {

    private static final String EVENTS_ENDPOINT = "/v1.26/events?since=0";

    private final DockerRestTemplate restTemplate;
    private final EventsMapper eventsMapper;

    @Autowired
    public EventsClient(final DockerRestTemplate restTemplate, final EventsMapper eventsMapper) {
        this.restTemplate = restTemplate;
        this.eventsMapper = eventsMapper;
    }

    public Flowable<Event> getEvents() {
        return this.restTemplate.getForEventStream(EVENTS_ENDPOINT)
            .map(this.eventsMapper::toEvent);
    }
}
