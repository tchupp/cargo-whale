package com.cargowhale.docker.client.core;

import com.cargowhale.docker.client.core.exception.ContainerNotFoundException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@Component
public class DockerErrorHandler extends DefaultResponseErrorHandler {

    @Override
    public void handleError(final ClientHttpResponse response) throws IOException {
        switch (response.getStatusCode()) {
            case NOT_FOUND:
                throw new ContainerNotFoundException();
            default:
                super.handleError(response);
        }
    }
}
