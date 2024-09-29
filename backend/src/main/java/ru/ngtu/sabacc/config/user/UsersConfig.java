package ru.ngtu.sabacc.config.user;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Egor Bokov
 */
@Configuration
@EnableConfigurationProperties({
        UserConfigProperties.class
})
public class UsersConfig {
}
