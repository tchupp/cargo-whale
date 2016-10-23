package com.cargowhale.docker.service;

import com.cargowhale.docker.config.CargoWhaleProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ContainerServiceTest {

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

}
