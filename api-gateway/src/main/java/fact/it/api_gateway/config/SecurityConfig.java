package fact.it.api_gateway.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

  @Bean
  public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
    serverHttpSecurity
        .cors(cors -> cors.configurationSource(corsConfigurationSource())) // Enable CORS
        .authorizeExchange(
            exchange -> exchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .pathMatchers(HttpMethod.GET).permitAll()
                .anyExchange().authenticated()) // Require authentication for other routes
        .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()));
    return serverHttpSecurity.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowedOrigins(
        Arrays.asList("http://localhost:5173", "http://localhost", "http://127.0.0.1", "http://0.0.0.0")); // Allowed
    // origins
    config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Allowed methods
    config.setAllowedHeaders(Arrays.asList("*")); // Allow all headers
    config.setAllowCredentials(true); // Allow cookies and credentials
    config.setMaxAge(3600L); // Cache pre-flight response for 1 hour

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
