package com.cargowhale.docker.config.docker;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cargowhale.docker", ignoreUnknownFields = false)
public class DockerProperties {

    private String uri;
    private Boolean enableEvents;

    public String getUri() {
        return this.uri;
    }

    public void setUri(final String uri) {
        this.uri = uri;
    }

    public Boolean getEnableEvents() {
        return this.enableEvents;
    }

    public void setEnableEvents(final Boolean enableEvents) {
        this.enableEvents = enableEvents;
    }
}
