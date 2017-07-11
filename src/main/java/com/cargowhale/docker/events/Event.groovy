package com.cargowhale.docker.events

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonValue
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.spotify.docker.client.jackson.UnixTimestampDeserializer
import groovy.transform.Canonical

@Canonical
@JsonInclude(JsonInclude.Include.NON_NULL)
class Event {

    @JsonCreator
    static Event build(
        @JsonProperty("Type") final Type type,
        @JsonProperty("Action") final String action,
        @JsonProperty("Actor") final Actor actor,
        @JsonProperty("time")
        @JsonDeserialize(using = UnixTimestampDeserializer.class) final Date time) {
        new Event(type, action, actor, time)
    }

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
        DAEMON("daemon"),
        NODE("node"),
        SERVICE("service")

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

