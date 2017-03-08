package com.cargowhale.docker.events;

import com.cargowhale.docker.config.docker.DockerProperties;
import io.reactivex.schedulers.Schedulers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventRepositoryConfiguration {

    private final EventClient client;
    private final DockerProperties properties;

    @Autowired
    public EventRepositoryConfiguration(final EventClient client, final DockerProperties properties) {
        this.client = client;
        this.properties = properties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "cargowhale.docker", name = "enable-events", matchIfMissing = true)
    public EventRepository eventRepository() {
        EventRepository eventRepository = new EventRepository(Schedulers.io());

        this.client.getEvents()
            .subscribeOn(Schedulers.io())
            .subscribe(eventRepository::addEvent);

        return eventRepository;
    }

    @Bean
    @ConditionalOnMissingBean(EventRepository.class)
    public EventRepository emptyEventRepository() {
        return new EventRepository(Schedulers.io());
    }
}
