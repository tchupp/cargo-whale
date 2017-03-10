package com.cargowhale.docker.client.events;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.spotify.docker.client.messages.Event;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.client.ClientHttpResponse;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class EventReaderTest {

    @Mock
    private ClientHttpResponse response;

    @Mock
    private JsonFactory factory;

    @InjectMocks
    private EventReader eventReader;

    @Test
    public void nextMessageReturnsValueAsEvent() throws Exception {
        Event event = mock(Event.class);
        JsonParser jsonParser = mock(JsonParser.class);
        InputStream inputStream = mock(InputStream.class);

        when(this.response.getBody()).thenReturn(inputStream);
        when(this.factory.createParser(inputStream)).thenReturn(jsonParser);
        when(jsonParser.readValueAs(Event.class)).thenReturn(event);

        when(jsonParser.nextToken()).thenReturn(JsonToken.VALUE_NULL);

        assertThat(this.eventReader.nextMessage(), is(event));
    }

    @Test
    public void nextMessageReturnsNull_IfParserIsClosed() throws Exception {
        Event event = mock(Event.class);
        JsonParser jsonParser = mock(JsonParser.class);
        InputStream inputStream = mock(InputStream.class);

        when(this.response.getBody()).thenReturn(inputStream);
        when(this.factory.createParser(inputStream)).thenReturn(jsonParser);
        when(jsonParser.readValueAs(Event.class)).thenReturn(event);
        when(jsonParser.isClosed()).thenReturn(true);

        assertThat(this.eventReader.nextMessage(), nullValue());
    }

    @Test
    public void nextMessageCallsIsClosedOnParser() throws Exception {
        JsonParser jsonParser = mock(JsonParser.class);
        InputStream inputStream = mock(InputStream.class);

        when(this.response.getBody()).thenReturn(inputStream);
        when(this.factory.createParser(inputStream)).thenReturn(jsonParser);

        this.eventReader.nextMessage();

        verify(jsonParser).isClosed();
    }

    @Test
    public void nextMessageCachesParserForFutureCalls() throws Exception {
        JsonParser jsonParser = mock(JsonParser.class);
        InputStream inputStream = mock(InputStream.class);

        when(this.response.getBody()).thenReturn(inputStream);
        when(this.factory.createParser(inputStream)).thenReturn(jsonParser);

        this.eventReader.nextMessage();
        this.eventReader.nextMessage();

        verify(this.factory, times(1)).createParser(inputStream);
    }

    @Test
    public void nextMessageReturnsNull_IfParserDoesNotHaveNextToken() throws Exception {
        Event event = mock(Event.class);
        JsonParser jsonParser = mock(JsonParser.class);
        InputStream inputStream = mock(InputStream.class);

        when(this.response.getBody()).thenReturn(inputStream);
        when(this.factory.createParser(inputStream)).thenReturn(jsonParser);
        when(jsonParser.readValueAs(Event.class)).thenReturn(event);

        when(jsonParser.nextToken()).thenReturn(null);

        assertThat(this.eventReader.nextMessage(), nullValue());
    }
}