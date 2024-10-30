package ru.ngtu.sabacc.system.config.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.StompWebSocketEndpointRegistration;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * @author Egor Bokov
 */
@Configuration
@EnableConfigurationProperties(WebsocketConfigProperties.class)
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    private final WebsocketConfigProperties websocketConfigProperties;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker(websocketConfigProperties
                .getBroker().getDestinationPrefixes());
        registry.setApplicationDestinationPrefixes(websocketConfigProperties
                .getBroker().getApplicationDestinationPrefixes());
        registry.setUserDestinationPrefix(websocketConfigProperties
                .getBroker().getUserDestinationPrefixes());
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        StompWebSocketEndpointRegistration stompWebSocketEndpointRegistration = registry
                .addEndpoint(websocketConfigProperties
                        .getStomp().getEndpoint())
                .setAllowedOrigins(websocketConfigProperties
                        .getStomp().getAllowedOrigin())
                .setAllowedOriginPatterns(websocketConfigProperties
                        .getStomp().getAllowedOriginPatterns());

        if(websocketConfigProperties.getStomp().isSockJsEnabled())
            stompWebSocketEndpointRegistration.withSockJS();
    }
}