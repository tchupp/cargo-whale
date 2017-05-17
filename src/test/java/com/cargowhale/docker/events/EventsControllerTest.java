package com.cargowhale.docker.events;

import io.jmnarloch.spring.boot.rxjava.config.RxJavaMvcAutoConfiguration;
import io.reactivex.Flowable;
import io.reactivex.processors.ReplayProcessor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static com.cargowhale.division.matchers.EventStreamMatcher.eventStreamContains;
import static com.cargowhale.docker.test.ControllerTestUtils.toJsonString;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(EventsController.class)
@ImportAutoConfiguration(RxJavaMvcAutoConfiguration.class)
public class EventsControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventsRepository eventsRepository;

    @MockBean
    private EventsResourceProcessor eventsResourceProcessor;

    @Test
    public void getEvents_ReturnsListOfEventsPublishedBeforeRequest() throws Exception {
        ReplayProcessor<Event> publisher = ReplayProcessor.create();
        Event event1 = new Event(Event.Type.CONTAINER, "start", new Event.Actor("me!"));
        Event event2 = new Event(Event.Type.NETWORK, "start", new Event.Actor("not me.."));
        Event event3 = new Event(Event.Type.NETWORK, "stop", new Event.Actor("me!"));
        Event event4 = new Event(Event.Type.CONTAINER, "stop", new Event.Actor("me!"));
        EventsResource expectedResponse = new EventsResource(asList(event1, event2, event3));

        given(this.eventsRepository.getAllEvents()).willReturn(Flowable.fromPublisher(publisher));
        given(this.eventsResourceProcessor.processPastEvents(any(EventsResource.class))).willReturn(expectedResponse);

        publisher.onNext(event1);
        publisher.onNext(event2);
        publisher.onNext(event3);

        MvcResult result = this.mvc.perform(get("/api/events").accept(MediaType.APPLICATION_JSON)).andReturn();
        ResultActions dispatch = this.mvc.perform(asyncDispatch(result));

        publisher.onNext(event4);

        dispatch
            .andExpect(status().isOk())
            .andExpect(content().string(toJsonString(expectedResponse)));
    }

    @Test
    public void followEvents_ReturnsStreamOfEventsPublishedAfterRequest() throws Exception {
        ReplayProcessor<Event> publisher = ReplayProcessor.create();
        Event event1 = new Event(Event.Type.CONTAINER, "start", new Event.Actor("me!"));
        Event event2 = new Event(Event.Type.NETWORK, "start", new Event.Actor("me!"));
        Event event3 = new Event(Event.Type.NETWORK, "stop", new Event.Actor("me!"));
        Event event4 = new Event(Event.Type.CONTAINER, "stop", new Event.Actor("me!"));

        given(this.eventsRepository.getAllEvents()).willReturn(Flowable.fromPublisher(publisher));

        publisher.onNext(event1);
        publisher.onNext(event2);
        publisher.onNext(event3);

        ResultActions resultActions = this.mvc.perform(get("/api/events?follow=true").accept(MediaType.TEXT_EVENT_STREAM));

        publisher.onNext(event4);

        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string(eventStreamContains(event1, event2, event3, event4)));
    }

    @Test
    public void getContainerEvents_ReturnsListOfContainerEventsPublishedBeforeRequest() throws Exception {
        ReplayProcessor<Event> publisher = ReplayProcessor.create();
        Event event1 = new Event(Event.Type.CONTAINER, "start", new Event.Actor("me!"));
        Event event2 = new Event(Event.Type.CONTAINER, "stop", new Event.Actor("me!"));
        EventsResource expectedResponse = new EventsResource(singletonList(event1));

        given(this.eventsRepository.getEventsByType(Event.Type.CONTAINER)).willReturn(Flowable.fromPublisher(publisher));
        given(this.eventsResourceProcessor.processPastContainerEvents(any(EventsResource.class))).willReturn(expectedResponse);

        publisher.onNext(event1);

        MvcResult result = this.mvc.perform(get("/api/events/containers").accept(MediaType.APPLICATION_JSON)).andReturn();
        ResultActions dispatch = this.mvc.perform(asyncDispatch(result));

        publisher.onNext(event2);

        dispatch
            .andExpect(status().isOk())
            .andExpect(content().string(toJsonString(expectedResponse)));
    }

    @Test
    public void followContainerEvents_ReturnsStreamOfContainerEventsPublishedAfterRequest() throws Exception {
        ReplayProcessor<Event> publisher = ReplayProcessor.create();
        Event event1 = new Event(Event.Type.CONTAINER, "start", new Event.Actor("me!"));
        Event event2 = new Event(Event.Type.CONTAINER, "stop", new Event.Actor("me!"));

        given(this.eventsRepository.getEventsByType(Event.Type.CONTAINER)).willReturn(Flowable.fromPublisher(publisher));

        publisher.onNext(event1);

        ResultActions resultActions = this.mvc.perform(get("/api/events/containers?follow=true").accept(MediaType.TEXT_EVENT_STREAM));

        publisher.onNext(event2);

        resultActions
            .andExpect(status().isOk())
            .andExpect(content().string(eventStreamContains(event1, event2)));
    }
}