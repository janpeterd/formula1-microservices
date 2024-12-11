package fact.it.api_gateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
    serverHttpSecurity // GET /drivers wont need auth all others will
        .authorizeExchange(exchange -> exchange.pathMatchers(HttpMethod.GET, "/drivers")
            .permitAll()
            .anyExchange()
            .authenticated()) // drivers
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(withDefaults()));
    return serverHttpSecurity.build();
  }
}
