package com.cargowhale.docker.events

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.Canonical
import org.springframework.hateoas.ResourceSupport

@Canonical
@JsonInclude(JsonInclude.Include.NON_EMPTY)
class EventsResource extends ResourceSupport {

    List<Event> events

    EventsResource(List<Event> events) {
        this.events = events
    }
}
