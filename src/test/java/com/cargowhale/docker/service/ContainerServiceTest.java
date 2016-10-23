package com.cargowhale.docker.service;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerServiceTest {

    private static final String socatUri = "http://socat:2375";

    @InjectMocks
    private ContainerService service;

    @Mock
    private CargoWhaleProperties properties;

    @Mock
    private RestTemplate template;

    @Test
    public void getAllContainersReturnsEveryContainerFromDockerApi(){
        final String expectedContainers = "ALL CONTAINERS";
        final String dockerUri = "http://this.is.docker:yo";

        when(this.properties.getDockerUri()).thenReturn(dockerUri);
        when(this.template.getForObject(dockerUri + "/containers/json?all=1", String.class)).thenReturn(expectedContainers);

        String actualContainers = this.service.getAllContainers();

        assertThat(actualContainers, is(expectedContainers));
    }

    @Test
    public void getFilteredContainersReturnsSelectedTypesOfContainers(){
        String expected = "ALL RUNNING CONTAINERS";
        String filter = "running";
        String filterString = createFilterString(filter);
        ResponseEntity<String> expectedEntity = new ResponseEntity<>("ALL RUNNING CONTAINERS", HttpStatus.OK);
        when(this.template.getForEntity(this.socatUri + "/containers/json?filters={filterString}",
                String.class, filterString)).thenReturn(expectedEntity);
        String actual = this.service.getFilteredContainers(filter);
        assertThat(actual, is(expected));
    }

    private String createFilterString(String filter){
        return "{\"status\":[\""+filter+"\"]}";
    }

    private class MockContainerService extends ContainerService{
        public MockContainerService(String socatUri) {
            this.socatUri = socatUri;
        }
    }
}

