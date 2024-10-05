package ar.edu.utn.frc.tup.lciii.configs;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Timer;

@Configuration
public class RestTemplateConfig {

    private final static int TIMEOUT = 5000;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setReadTimeout(Duration.ofMillis(TIMEOUT))
                .setConnectTimeout(Duration.ofMillis(TIMEOUT))
                .build();
    }
}
