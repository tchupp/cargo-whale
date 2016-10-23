package com.cargowhale.docker.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "cargowhale", ignoreUnknownFields = false)
public class CargoWhaleProperties {

    private final Docker docker = new Docker();

    public Docker getDocker() {
        return docker;
    }

    public String getDockerUri() {
        return this.docker.getUri();
    }

    public static class Docker {

        private String uri;

        public String getUri() {
            return uri;
        }

        public void setUri(final String uri) {
            this.uri = uri;
        }
    }
}
