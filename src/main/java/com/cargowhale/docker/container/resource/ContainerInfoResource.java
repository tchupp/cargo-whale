package com.cargowhale.docker.container.resource;

import com.cargowhale.docker.container.ContainerState;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

public class ContainerInfoResource extends ResourceSupport {

    private final List<String> names;
    private final String image;
    private final ContainerState state;

    public ContainerInfoResource(final List<String> names, final String image, final ContainerState state) {
        this.names = names;
        this.image = image;
        this.state = state;
    }

    public List<String> getNames() {
        return this.names;
    }

    public String getImage() {
        return this.image;
    }

    public ContainerState getState() {
        return this.state;
    }
}
