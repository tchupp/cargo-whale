package com.cargowhale.docker.events;

import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventsConfiguration {

    private final EventsClient client;

    @Autowired
    public EventsConfiguration(final EventsClient client) {
        this.client = client;
    }

    @Bean
    @ConditionalOnProperty(prefix = "cargowhale.docker", name = "enable-events", matchIfMissing = true)
    public EventsRepository eventRepository() {
        EventsRepository eventsRepository = new EventsRepository();

        this.client.getAllEvents()
            .subscribeOn(Schedulers.io())
            .subscribe(eventsRepository::addEvent);

        return eventsRepository;
    }

    @Bean
    @ConditionalOnMissingBean(EventsRepository.class)
    public EventsRepository emptyEventRepository() {
        return new EventsRepository();
    }

    @Bean
    public EventsService eventsService(final EventsClient eventClient, final EventsRepository eventRepository) {
        return new EventsService(eventClient, eventRepository, Schedulers.io());
    }
}
