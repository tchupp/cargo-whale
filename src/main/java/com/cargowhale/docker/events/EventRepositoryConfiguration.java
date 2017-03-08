package com.cargowhale.docker.events;

import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventRepositoryConfiguration {

    private final EventClient client;

    @Autowired
    public EventRepositoryConfiguration(final EventClient client) {
        this.client = client;
    }

    @Bean
    public EventRepository eventStore() {
        EventRepository eventRepository = new EventRepository(Schedulers.io());

        this.client.getEvents()
            .subscribeOn(Schedulers.io())
            .subscribe(eventRepository::addEvent);

        return eventRepository;
    }
}
