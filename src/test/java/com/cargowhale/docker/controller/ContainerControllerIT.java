package com.cargowhale.docker.controller;

import com.cargowhale.docker.CargoWhaleDockerApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CargoWhaleDockerApplication.class)
public class ContainerControllerIT {
    @Test
    public void getFilteredContainersWithBadFilterReturns400(){

    }
}
