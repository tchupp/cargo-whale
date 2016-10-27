package com.cargowhale.docker.domain;

public class ChangeStatusResponse {

    private String name;

    public ChangeStatusResponse() {
    }

    public ChangeStatusResponse(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public ChangeStatusResponse setName(String status) {
        this.name = status;
        return this;
    }
}
