package com.ajaib.coin.market.maker.config;

import io.micrometer.core.instrument.MeterRegistry;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.util.StringUtils;

@Configuration
public class KafkaProducerConfig {

  @Autowired
  private KafkaProperties kafkaProperties;

  @Autowired
  private MeterRegistry meterRegistry;

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate() {
    return new KafkaTemplate<>(producerFactory());
  }

  private ProducerFactory<String, String> producerFactory() {
    String kafkaBrokerList =
        StringUtils.collectionToCommaDelimitedString(kafkaProperties.getBootstrapServers());
    Map<String, Object> props = new HashMap<>();
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaBrokerList);
    props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

    DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(props);
    factory.addListener(new MicrometerProducerListener<>(meterRegistry));
    return factory;
  }

}
