package fact.it.api_gateway.config;

import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class RateLimitingConfig {
  private static final Logger logger = LoggerFactory.getLogger(RateLimitingConfig.class);

  @Bean
  KeyResolver userKeyResolver() {
    return exchange -> {
      String ip = exchange.getRequest().getRemoteAddress().getAddress().getHostAddress();
      logger.info("Rate limiting request from IP: {}", ip);
      return Mono.just(ip);
    };
  }

  @Bean
  public RedisRateLimiter redisRateLimiter() {
    return new RedisRateLimiter(1, 1, 1);
  }
}
