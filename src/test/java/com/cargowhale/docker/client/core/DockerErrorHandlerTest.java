package com.cargowhale.docker.client.core;

import com.cargowhale.docker.client.core.exception.ContainerNotFoundException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.mock.http.client.MockClientHttpResponse;

@RunWith(MockitoJUnitRunner.class)
public class DockerErrorHandlerTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @InjectMocks
    private DockerErrorHandler errorHandler;

    @Test
    public void throwsContainerNotFoundException_With404Status() throws Exception {
        this.thrown.expect(ContainerNotFoundException.class);

        MockClientHttpResponse response = new MockClientHttpResponse(new byte[1], HttpStatus.NOT_FOUND);

        this.errorHandler.handleError(response);
    }
}