package com.ajaib.coin.market.maker.config;

import com.ajaib.coin.market.maker.constant.CacheConstant;
import com.ajaib.coin.market.maker.property.CacheProperties;
import com.ajaib.coin.market.maker.property.CacheProperties.CacheNames;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Map;
import org.springframework.integration.redis.util.RedisLockRegistry;

@Configuration
@EnableCaching
public class CacheConfig {

  private static final String PONG = "PONG";

  @Autowired
  private RedisProperties redisProperties;

  @Value("${spring.cache.redis.time-to-live}")
  private Duration DEFAULT_TTL;

  @Value("${spring.cache.redis.key-prefix}")
  private String PREFIX_KEY;

  @Autowired
  private CacheProperties cacheProperties;

  @Bean
  public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
    final Map<String, RedisCacheConfiguration> cacheConfigurations =
        cacheProperties.getCacheNames()
            .stream()
            .collect(Collectors.toMap(CacheNames::getKey,
                cacheNames -> redisCacheConfiguration(cacheNames.getExpiryTtl())));

    return RedisCacheManager.RedisCacheManagerBuilder.fromConnectionFactory(connectionFactory)
        .cacheDefaults(redisCacheConfiguration(DEFAULT_TTL))
        .withInitialCacheConfigurations(cacheConfigurations)
        .build();
  }

  private RedisCacheConfiguration redisCacheConfiguration(Duration duration) {
    final CacheKeyPrefix cacheKeyPrefix = cacheName -> PREFIX_KEY + ":" + cacheName + ":";

    return RedisCacheConfiguration.defaultCacheConfig()
        .entryTtl(duration)
        .computePrefixWith(cacheKeyPrefix);
  }

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration =
        new RedisStandaloneConfiguration(redisProperties.getHost(), redisProperties.getPort());
    redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));
    return new JedisConnectionFactory(redisStandaloneConfiguration);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(this.jedisConnectionFactory());
    return template;
  }

  @Bean
  public StringRedisTemplate stringRedisTemplate() {
    StringRedisTemplate template = new StringRedisTemplate();
    template.setConnectionFactory(this.jedisConnectionFactory());
    return template;
  }

  @Bean
  public HealthIndicator redisHealthIndicator() {
    return () -> {
      final long start = System.currentTimeMillis();
      RedisConnection connection = null;
      try {
        connection = RedisConnectionUtils.getConnection(this.jedisConnectionFactory());
        final String response = connection.ping();
        final Builder builder = PONG.equals(response) ? Health.up() : Health.down();
        return builder.withDetail("time", System.currentTimeMillis() - start)
            .withDetail("response", response).build();
      } catch (final Exception e) {
        return Health.down().withDetail("time", System.currentTimeMillis() - start)
            .withDetail("error", e.getMessage()).build();
      } finally {
        RedisConnectionUtils.releaseConnection(connection, this.jedisConnectionFactory());
      }
    };
  }

  @Bean
  public RedisLockRegistry redisLockRegistry() {
    return new RedisLockRegistry(this.jedisConnectionFactory(),
        PREFIX_KEY + CacheConstant.LOCK_KEY_PREFIX);
  }

}
