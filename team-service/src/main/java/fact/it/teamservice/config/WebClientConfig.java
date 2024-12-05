package fact.it.teamservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    // @Bean annotation:
    //  - singleton
    //  - injectable via depency injection?
    @Bean
    public WebClient webClient() {
        return WebClient.builder().build();
    }
}