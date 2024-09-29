package ru.ngtu.sabacc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.ngtu.sabacc.config.user.UserConfigProperties;

@SpringBootApplication
public class SabaccApplication {

    public static void main(String[] args) {
        SpringApplication.run(SabaccApplication.class, args);
    }

}
