package com.ajaib.coin.market.maker.util;

import com.ajaib.coin.market.maker.enums.MetricType;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MetricPublisher {

  private final MeterRegistry meterRegistry;

  public void incrementCounter(MetricType counterType, int incrementBy, Tag... tags) {
    try {
      meterRegistry.counter(counterType.getValue(), Tags.of(tags)).increment(incrementBy);
    } catch (Exception e) {
      log.error("Error in MetricPublisher", e);
    }
  }

  public void incrementCounter(MetricType counterType, double incrementBy, Tag... tags) {
    try {
      meterRegistry.counter(counterType.getValue(), Tags.of(tags)).increment(incrementBy);
    } catch (Exception e) {
      log.error("Error in MetricPublisher", e);
    }
  }

  public void incrementCounter(MetricType counterType, Tag... tags) {
    try {
      meterRegistry.counter(counterType.getValue(), Tags.of(tags)).increment(1);
    } catch (Exception e) {
      log.error("Error in MetricPublisher", e);
    }
  }

  public <T extends Number> void setGauge(MetricType gaugeType, T number, Tag... tags) {
    try {
      this.meterRegistry.gauge(gaugeType.getValue(), Tags.of(tags), number);
    } catch (Exception e) {
      log.error("Error in MetricPublisher", e);
    }
  }
}