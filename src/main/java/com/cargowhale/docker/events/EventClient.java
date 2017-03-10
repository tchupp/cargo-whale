package com.cargowhale.docker.events;

import com.cargowhale.docker.client.core.DockerRestTemplate;
import com.spotify.docker.client.messages.Event;
import io.reactivex.Flowable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class EventClient {

    private static final String EVENTS_ENDPOINT = "/v1.26/events?since=0";

    private final DockerRestTemplate restTemplate;

    @Autowired
    public EventClient(final DockerRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Flowable<Event> getEvents() {
        return this.restTemplate.getForEventStream(EVENTS_ENDPOINT);
    }
}
