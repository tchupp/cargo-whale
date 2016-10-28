package com.cargowhale.docker.container;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContainerInfoVM {

    private final List<String> names;
    private final String image;
    private final ContainerState state;

    public ContainerInfoVM(@JsonProperty("Names") final List<String> names,
                           @JsonProperty("Image") final String image,
                           @JsonProperty("State") final ContainerState state) {
        this.names = names;
        this.image = image;
        this.state = state;
    }

    public List<String> getNames() {
        return names;
    }

    public String getImage() {
        return image;
    }

    public ContainerState getState() {
        return state;
    }
}
