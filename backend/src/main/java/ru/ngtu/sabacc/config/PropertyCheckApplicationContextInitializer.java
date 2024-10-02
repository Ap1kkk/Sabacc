package ru.ngtu.sabacc.config;

import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.util.Strings;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class PropertyCheckApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    private static final List<String> PROPERTIES = List.of(
            "websocket.stomp.endpoint",
            "websocket.stomp.allowed-origin",
            "websocket.broker.destination-prefixes",
            "websocket.broker.application-destination-prefixes",
            "user.expiration-in-hours",
            "server.host",
            "server.protocol"
    );


    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        PROPERTIES.forEach(property -> validateProperty(environment.getProperty(property), property));
    }

    private void validateProperty(String propertyValue, String propertyName) {
        if(isEmpty(propertyValue))
            throw new IllegalStateException(
                    String.format("Property '%s' is required", propertyName)
            );
    }

    private boolean isEmpty(String value) {
        return Strings.isBlank(value) || value.trim().isEmpty();
    }
}
