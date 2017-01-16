package com.cargowhale.docker.test;

import com.cargowhale.docker.container.info.index.ContainerIndexResource;
import org.hamcrest.Matchers;
import org.springframework.hateoas.Link;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControllerTestUtils {

    public static void setupMockRequestContextHolder() {
        String localHost = "http://localhost";
        HttpServletRequest httpServletRequestMock = mock(HttpServletRequest.class);

        when(httpServletRequestMock.getRequestURL()).thenReturn(new StringBuffer(localHost));
        when(httpServletRequestMock.getRequestURI()).thenReturn(localHost);
        when(httpServletRequestMock.getHeaderNames()).thenReturn(Collections.emptyEnumeration());
        when(httpServletRequestMock.getContextPath()).thenReturn("");
        when(httpServletRequestMock.getServletPath()).thenReturn("");

        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(httpServletRequestMock);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    public static void verifyLink(final ContainerIndexResource containerIndex, final String rel, final String path) {
        assertThat(containerIndex.hasLink(rel), is(true));
        Link upLink = containerIndex.getLink(rel);
        assertThat(upLink.getHref(), Matchers.endsWith(path));
    }
}
