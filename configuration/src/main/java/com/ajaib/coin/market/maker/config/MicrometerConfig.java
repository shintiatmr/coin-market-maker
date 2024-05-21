package com.ajaib.coin.market.maker.config;

import com.newrelic.telemetry.micrometer.NewRelicRegistry;
import com.newrelic.telemetry.micrometer.NewRelicRegistryConfig;
import io.micrometer.core.instrument.util.NamedThreadFactory;
import java.net.UnknownHostException;
import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.export.simple.SimpleMetricsExportAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore({
    CompositeMeterRegistryAutoConfiguration.class,
    SimpleMetricsExportAutoConfiguration.class
})
@AutoConfigureAfter(MetricsAutoConfiguration.class)
@ConditionalOnClass(NewRelicRegistry.class)
public class MicrometerConfig {

  @Bean
  public NewRelicRegistryConfig newRelicConfig() {
    return new NewRelicRegistryConfig() {
      @Value("${management.metrics.tags.newrelic-application}")
      String serviceName;

      @Value("${management.metrics.export.newrelic.api-key}")
      String apiKey;

      @Value("${management.metrics.export.newrelic.uri}")
      String uri;

      @Override
      public String get(String key) {
        return null;
      }

      @Override
      public String apiKey() {
        return this.apiKey;
      }

      @Override
      public String uri() {
        return this.uri;
      }

      @Override
      public Duration step() {
        return Duration.ofSeconds(5);
      }

      @Override
      public String serviceName() {
        return this.serviceName;
      }
    };
  }

  @Bean
  public NewRelicRegistry newRelicMeterRegistry(NewRelicRegistryConfig config)
      throws UnknownHostException {
    NewRelicRegistry newRelicRegistry =
        NewRelicRegistry.builder(config).build();
    newRelicRegistry.start(new NamedThreadFactory("newrelic.micrometer.registry"));
    return newRelicRegistry;
  }
}