package com.cargowhale.docker.domain;

public class ChangeStateRequest {

    private String status;

    public ChangeStateRequest() {
    }

    public ChangeStateRequest(final String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }

    public ChangeStateRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
