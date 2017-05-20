package com.cargowhale.docker.events

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonValue
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_NULL)
class Event {

    Type type
    String action
    Actor actor
    Date time

    @Canonical
    static class Actor {

        String id
        Map<String, String> attributes

    }

    enum Type {
        CONTAINER("container"),
        IMAGE("image"),
        VOLUME("volume"),
        NETWORK("network"),
        DAEMON("daemon")

        private final String name

        @JsonCreator
        Type(final String name) {
            this.name = name
        }

        @JsonValue
        String getName() {
            return name
        }
    }
}

