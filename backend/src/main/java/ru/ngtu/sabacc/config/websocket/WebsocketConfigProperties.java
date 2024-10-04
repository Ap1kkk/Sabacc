package ru.ngtu.sabacc.config.websocket;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("websocket")
public class WebsocketConfigProperties {
    private Stomp stomp;
    private Broker broker;

    @Data
    public static class Stomp {
        private String endpoint = "/ws";
        private String allowedOrigin;
        private String allowedOriginPatterns;
        private boolean sockJsEnabled = true;
    }

    @Data
    public static class Broker {
        private String[] destinationPrefixes;
        private String[] applicationDestinationPrefixes;
    }
}