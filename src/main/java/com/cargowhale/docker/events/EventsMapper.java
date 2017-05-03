package com.cargowhale.docker.events;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface EventsMapper {

    /**
     * EVENT
     */

    @Mappings({
        @Mapping(target = "type", expression = "java(toEventType(event.type()))"),
        @Mapping(target = "action", expression = "java(event.action())"),
        @Mapping(target = "actor", expression = "java(toEventActor(event.actor()))"),
        @Mapping(target = "time", expression = "java(event.time())")
    })
    Event toEvent(final com.spotify.docker.client.messages.Event event);

    /**
     * EVENT TYPE
     */

    Event.Type toEventType(final com.spotify.docker.client.messages.Event.Type type);

    /**
     * EVENT ACTOR
     */

    @Mappings({
        @Mapping(target = "id", expression = "java(actor.id())"),
        @Mapping(target = "attributes", expression = "java(actor.attributes())")
    })
    Event.Actor toEventActor(final com.spotify.docker.client.messages.Event.Actor actor);
}
