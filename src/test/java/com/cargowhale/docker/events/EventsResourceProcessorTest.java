package com.cargowhale.docker.events;

import com.cargowhale.docker.test.ControllerTestUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.hateoas.Link;

import java.util.ArrayList;

import static com.cargowhale.docker.test.ControllerTestUtils.verifyLink;

@RunWith(MockitoJUnitRunner.class)
public class EventsResourceProcessorTest {

    @InjectMocks
    private EventsResourceProcessor resourceProcessor;

    @Before
    public void setUp() throws Exception {
        ControllerTestUtils.setupMockRequestContextHolder();
    }

    @Test
    public void processPastEvents_ReturnsEventsResourceWithPastEventsLink() throws Exception {
        EventsResource eventsResource = this.resourceProcessor.processPastEvents(new EventsResource(new ArrayList<>()));

        verifyLink(eventsResource, Link.REL_SELF, "/api/events");
        verifyLink(eventsResource, "container", "/api/events/container");
    }

    @Test
    public void processPastContainerEvents_ReturnsEventsResourceWithPastContainerEventsLink() throws Exception {
        EventsResource eventsResource = this.resourceProcessor.processPastContainerEvents(new EventsResource(new ArrayList<>()));

        verifyLink(eventsResource, "up", "/api/events");
        verifyLink(eventsResource, Link.REL_SELF, "/api/events/container");
    }
}