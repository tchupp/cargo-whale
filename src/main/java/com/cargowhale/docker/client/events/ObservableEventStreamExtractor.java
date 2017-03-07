package com.cargowhale.docker.client.events;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spotify.docker.client.ObjectMapperProvider;
import com.spotify.docker.client.messages.Event;
import io.reactivex.Emitter;
import io.reactivex.Flowable;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;

public class ObservableEventStreamExtractor implements ResponseExtractor<Flowable<Event>> {

    @Override
    public Flowable<Event> extractData(final ClientHttpResponse response) throws IOException {
        EventReader eventReader = new EventReader(response, getDockerJsonFactory());

        return Flowable.generate((Emitter<Event> emitter) -> {
            Event event = eventReader.nextMessage();

            if (event == null) {
                emitter.onComplete();
            } else {
                emitter.onNext(event);
            }
        }).doOnError(__ -> eventReader.close());
    }

    private static JsonFactory getDockerJsonFactory() {
        return getDockerObjectMapper().getFactory();
    }

    private static ObjectMapper getDockerObjectMapper() {
        ObjectMapperProvider provider = new ObjectMapperProvider();
        return provider.getContext(ObjectMapper.class);
    }
}
