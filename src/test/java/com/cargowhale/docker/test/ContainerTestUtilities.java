package com.cargowhale.docker.test;

import com.cargowhale.docker.container.info.ContainerState;
import com.spotify.docker.client.messages.Container;
import org.springframework.test.util.ReflectionTestUtils;

public class ContainerTestUtilities {

    public static Container buildContainerWithState(final ContainerState state) {
        Container container = new Container();
        ReflectionTestUtils.setField(container, "state", state.getState());
        return container;
    }

    public static Container buildContainerWithId(final String id) {
        Container container = new Container();
        ReflectionTestUtils.setField(container, "id", id);
        return container;
    }
}
