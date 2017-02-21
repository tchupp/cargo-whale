package com.cargowhale.docker.client.logs;

import com.google.common.base.Charsets;
import com.spotify.docker.client.LogReader;
import com.spotify.docker.client.LogStream;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

public class LogStreamMessageConverter extends AbstractHttpMessageConverter<LogStream> {

    public LogStreamMessageConverter() {
        super(Charsets.UTF_8, MediaType.TEXT_PLAIN, MediaType.ALL);
    }

    @Override
    protected boolean supports(final Class<?> clazz) {
        return LogStream.class == clazz || LogStream.class.isAssignableFrom(clazz);
    }

    @Override
    protected LogStream readInternal(final Class<? extends LogStream> clazz, final HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        byte[] contentBuffer = StreamUtils.copyToByteArray(inputMessage.getBody());
        ByteArrayInputStream inputStream = new ByteArrayInputStream(contentBuffer);
        LogReader logReader = new LogReader(inputStream);

        return new ContainerLogStream(logReader);
    }

    @Override
    protected void writeInternal(final LogStream logStream, final HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        StreamUtils.copy(logStream.readFully(), Charsets.UTF_8, outputMessage.getBody());
    }
}
