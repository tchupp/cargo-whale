package com.cargowhale.docker.client.logs;

import com.google.common.base.Throwables;
import com.google.common.collect.AbstractIterator;
import com.google.common.io.Closer;
import com.spotify.docker.client.LogMessage;
import com.spotify.docker.client.LogReader;
import com.spotify.docker.client.LogStream;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import static com.google.common.base.Charsets.UTF_8;

public class ContainerLogStream extends AbstractIterator<LogMessage> implements LogStream {

    private final LogReader reader;

    ContainerLogStream(final LogReader reader) {
        this.reader = reader;
    }

    @Override
    protected LogMessage computeNext() {
        final LogMessage message;

        try {
            message = this.reader.nextMessage();
        } catch (final IOException e) {
            throw Throwables.propagate(e);
        }

        if (message == null) {
            return endOfData();
        }

        return message;
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        } catch (final IOException e) {
            throw Throwables.propagate(e);
        }
    }

    @Override
    public String readFully() {
        final StringBuilder stringBuilder = new StringBuilder();
        while (hasNext()) {
            stringBuilder.append(UTF_8.decode(next().content()));
        }
        return stringBuilder.toString();
    }

    @Override
    public void attach(final OutputStream stdout, final OutputStream stderr) throws IOException {
        attach(stdout, stderr, true);
    }

    @Override
    public void attach(final OutputStream stdout, final OutputStream stderr, final boolean closeAtEof) throws IOException {
        final Closer closer = Closer.create();
        try {
            if (closeAtEof) {
                closer.register(stdout);
                closer.register(stderr);
            }

            while (this.hasNext()) {
                final LogMessage message = this.next();
                final ByteBuffer content = message.content();

                switch (message.stream()) {
                    case STDOUT:
                        writeAndFlush(content, stdout);
                        break;
                    case STDERR:
                        writeAndFlush(content, stderr);
                        break;
                    case STDIN:
                    default:
                        break;
                }
            }
        } catch (final Throwable t) {
            throw closer.rethrow(t);
        } finally {
            closer.close();
        }
    }

    private static void writeAndFlush(
        final ByteBuffer buffer, final OutputStream outputStream) throws IOException {

        if (buffer.hasArray()) {
            outputStream.write(buffer.array(), buffer.position(), buffer.remaining());
        } else {
            // cannot access underlying byte array, need to copy into a temporary array
            while (buffer.hasRemaining()) {
                // figure out how much to read, but use an upper limit of 8kb. LogMessages should be rather
                // small so we don't expect this to get hit but avoid large temporary buffers, just in case.
                final int size = Math.min(buffer.remaining(), 8 * 1024);
                final byte[] chunk = new byte[size];
                buffer.get(chunk);
                outputStream.write(chunk);
            }
        }
        outputStream.flush();
    }
}
