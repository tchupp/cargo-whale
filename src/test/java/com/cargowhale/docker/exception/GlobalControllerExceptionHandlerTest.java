package com.cargowhale.docker.exception;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GlobalControllerExceptionHandlerTest {

    @InjectMocks
    private GlobalControllerExceptionHandler exceptionHandler;

    @Test
    public void handleBadFilterCorrectlyFormatsErrorMessage() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        BindException bindException = mock(BindException.class);

        String path = "/some/okay/uri";
        String message = "Bad Filter";

        when(request.getRequestURI()).thenReturn(path);

        ResponseEntity<CargoWhaleErrorMessage> responseEntity = this.exceptionHandler.handleBadFilter(request, bindException);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.BAD_REQUEST));

        CargoWhaleErrorMessage errorMessage = responseEntity.getBody();

        assertThat(errorMessage.getPath(), is(path));
        assertThat(errorMessage.getMessage(), is(message));
        assertThat(errorMessage.getError(), is(bindException.getClass().toString()));
    }
}