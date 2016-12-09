package com.cargowhale.docker.domain;

public class ChangeStateResponse {

    private String name;

    public ChangeStateResponse() {
    }

    public ChangeStateResponse(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ChangeStateResponse setName(String status) {
        this.name = status;
        return this;
    }
}
