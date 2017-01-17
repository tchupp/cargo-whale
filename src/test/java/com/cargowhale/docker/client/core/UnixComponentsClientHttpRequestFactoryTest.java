package com.cargowhale.docker.client.core;

import com.spotify.docker.client.UnixConnectionSocketFactory;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.protocol.HttpContext;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpMethod;

import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

public class UnixComponentsClientHttpRequestFactoryTest {

    private static final String DOCKER_URI = "unix:///var/run/docker.sock";
    private static final String SOCKET_FACTORY_REGISTRY = "http.socket-factory-registry";

    private UnixComponentsClientHttpRequestFactory requestFactory;

    @Before
    public void setUp() throws Exception {
        this.requestFactory = new UnixComponentsClientHttpRequestFactory(DOCKER_URI);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createsHttpContextWithPlainSocketFactory() throws Exception {
        HttpContext context = this.requestFactory.createHttpContext(HttpMethod.GET, URI.create("http://google.com"));

        Object attribute = context.getAttribute(SOCKET_FACTORY_REGISTRY);
        assertThat(attribute, instanceOf(Registry.class));

        Registry<ConnectionSocketFactory> registry = (Registry<ConnectionSocketFactory>) attribute;
        ConnectionSocketFactory httpFactory = registry.lookup("http");

        assertThat(httpFactory, instanceOf(PlainConnectionSocketFactory.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createsHttpsContextWithSSLSocketFactory() throws Exception {
        HttpContext context = this.requestFactory.createHttpContext(HttpMethod.GET, URI.create("http://google.com"));

        Object attribute = context.getAttribute(SOCKET_FACTORY_REGISTRY);
        assertThat(attribute, instanceOf(Registry.class));

        Registry<ConnectionSocketFactory> registry = (Registry<ConnectionSocketFactory>) attribute;
        ConnectionSocketFactory httpsFactory = registry.lookup("https");

        assertThat(httpsFactory, instanceOf(SSLConnectionSocketFactory.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createsUnixContextWithUnixSocketFactory_UriSchemeIsUnix() throws Exception {
        HttpContext context = this.requestFactory.createHttpContext(HttpMethod.GET, URI.create("unix://google.com"));

        Object attribute = context.getAttribute(SOCKET_FACTORY_REGISTRY);
        assertThat(attribute, instanceOf(Registry.class));

        Registry<ConnectionSocketFactory> registry = (Registry<ConnectionSocketFactory>) attribute;
        ConnectionSocketFactory unixFactory = registry.lookup("unix");

        assertThat(unixFactory, instanceOf(UnixConnectionSocketFactory.class));
    }

    @Test
    public void createsUnixContextWithUnixSocketFactory_CorrectUri() throws Exception {
        UnixComponentsClientHttpRequestFactory spy = spy(this.requestFactory);

        spy.createHttpContext(HttpMethod.GET, URI.create("unix://google.com"));

        verify(spy).buildUnixSocketFactory(URI.create(DOCKER_URI));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createsUnixContextWithUnixSocketFactory_UriSchemeIsNotUnix() throws Exception {
        HttpContext context = this.requestFactory.createHttpContext(HttpMethod.GET, URI.create("http://google.com"));

        Object attribute = context.getAttribute(SOCKET_FACTORY_REGISTRY);
        assertThat(attribute, instanceOf(Registry.class));

        Registry<ConnectionSocketFactory> registry = (Registry<ConnectionSocketFactory>) attribute;
        ConnectionSocketFactory unixFactory = registry.lookup("unix");

        assertThat(unixFactory, nullValue());
    }

    @Test
    public void returnsHttpMethodWithCorrectUri_UnixScheme() throws Exception {
        HttpUriRequest request = this.requestFactory.createHttpUriRequest(HttpMethod.GET, URI.create(DOCKER_URI + "/containers/json"));

        assertThat(request.getURI(), is(URI.create("unix://localhost:80/containers/json")));
    }
}