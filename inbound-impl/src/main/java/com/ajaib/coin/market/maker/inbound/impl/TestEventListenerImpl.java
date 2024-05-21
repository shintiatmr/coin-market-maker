package com.ajaib.coin.market.maker.inbound.impl;

import static net.logstash.logback.argument.StructuredArguments.kv;
import com.ajaib.coin.market.maker.constant.MessageConstant;
import com.ajaib.coin.market.maker.enums.MetricDimensionTags;
import com.ajaib.coin.market.maker.enums.MetricType;
import com.ajaib.coin.market.maker.inbound.TestEventListener;
import com.ajaib.coin.market.maker.message.AjaibEventMessage;
import com.ajaib.coin.market.maker.util.MapperHelper;
import com.ajaib.coin.market.maker.util.MetricPublisher;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "application.kafka.listener.test-event.enabled", havingValue = "true")
public class TestEventListenerImpl implements TestEventListener {

  private final MapperHelper mapperHelper;
  private final MetricPublisher metricPublisher;

  @KafkaListener(topics = MessageConstant.TEST_EVENT)
  public void onReceive(String json,
      @Header(value = KafkaHeaders.DELIVERY_ATTEMPT, required = false) Integer deliveryAttempt,
      @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
    try {
      log.info("listening message ",
          kv("topic", topic),
          kv("retryAttempt", deliveryAttempt),
          kv("eventPayload", json)
      );

      final AjaibEventMessage orderBuyMessage = mapperHelper.mapBean(json,
          AjaibEventMessage.class);



    } catch (Exception e) {
      Tag tag = new ImmutableTag(MetricDimensionTags.KAFKA_TOPIC.getValue(),
          MessageConstant.TEST_EVENT);
      metricPublisher.incrementCounter(MetricType.KAFKA_ERRORS, tag);
      log.error("error listening message ",
          kv("topic", topic),
          kv("retryAttempt", deliveryAttempt),
          kv("eventPayload", json),
          e
      );
      throw e;
    }
  }

}
