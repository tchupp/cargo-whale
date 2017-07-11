package com.cargowhale.docker.client.events;

import com.cargowhale.docker.events.Event;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.docker.client.ObjectMapperProvider;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;

public class ObservableEventStreamExtractor implements ResponseExtractor<Flowable<Event>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ObservableEventStreamExtractor.class);

    @Override
    public Flowable<Event> extractData(final ClientHttpResponse response) throws IOException {
        return Flowable.create(subscriber -> {
            try (EventReader eventReader = new EventReader(response, getDockerJsonFactory())) {
                while (!subscriber.isCancelled()) {
                    Event event = eventReader.nextMessage();

                    if (event == null) {
                        subscriber.onComplete();
                        break;
                    } else {
                        subscriber.onNext(event);
                    }
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    private static JsonFactory getDockerJsonFactory() {
        return getDockerObjectMapper().getFactory();
    }

    private static ObjectMapper getDockerObjectMapper() {
        ObjectMapperProvider provider = new ObjectMapperProvider();
        return provider.getContext(ObjectMapper.class);
    }
}
