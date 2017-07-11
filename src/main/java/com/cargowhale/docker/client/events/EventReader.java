package com.cargowhale.docker.client.events;

import com.cargowhale.docker.events.Event;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import org.springframework.http.client.ClientHttpResponse;

import java.io.Closeable;
import java.io.IOException;

public class EventReader implements Closeable {

    private final ClientHttpResponse response;
    private final JsonFactory factory;

    private JsonParser parser;

    EventReader(final ClientHttpResponse response, final JsonFactory factory) {
        this.response = response;
        this.factory = factory;
    }

    Event nextMessage() throws IOException {
        if (this.parser == null) {
            this.parser = this.factory.createParser(this.response.getBody());
        }

        if (this.parser.isClosed()) {
            return null;
        }

        if (this.parser.nextToken() == null) {
            return null;
        }

        return this.parser.readValueAs(Event.class);
    }

    @Override
    public void close() throws IOException {
        this.response.close();
    }
}