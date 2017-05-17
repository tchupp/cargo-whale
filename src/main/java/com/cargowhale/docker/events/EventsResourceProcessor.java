package com.cargowhale.docker.events;

import org.springframework.stereotype.Service;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Service
public class EventsResourceProcessor {

    EventsResource processPastEvents(final EventsResource resource) {
        resource.add(linkTo(methodOn(EventsController.class).getPastEvents()).withSelfRel());
        resource.add(linkTo(methodOn(EventsController.class).getPastContainerEvents()).withRel("container"));
        return resource;
    }

    EventsResource processPastContainerEvents(final EventsResource resource) {
        resource.add(linkTo(methodOn(EventsController.class).getPastContainerEvents()).withSelfRel());
        resource.add(linkTo(methodOn(EventsController.class).getPastEvents()).withRel("up"));
        return resource;
    }
}
