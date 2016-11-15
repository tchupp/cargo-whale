package com.cargowhale.docker.exception;

public class CargoWhaleErrorMessage {

    private final String path;
    private final String message;
    private final String error;

    public CargoWhaleErrorMessage(final String path, final String message, final String error) {
        this.path = path;
        this.message = message;
        this.error = error;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public String getError() {
        return error;
    }
}
