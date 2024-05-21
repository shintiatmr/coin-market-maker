package com.ajaib.coin.market.maker.config;

import static net.logstash.logback.argument.StructuredArguments.kv;
import com.ajaib.coin.market.maker.constant.KafkaConstant;
import com.ajaib.coin.market.maker.constant.MessageConstant;
import com.ajaib.coin.market.maker.enums.MetricDimensionTags;
import com.ajaib.coin.market.maker.enums.MetricType;
import com.ajaib.coin.market.maker.exception.KafkaRetryException;
import com.ajaib.coin.market.maker.property.KafkaRetryProperties;
import com.ajaib.coin.market.maker.util.MetricPublisher;
import io.micrometer.core.instrument.ImmutableTag;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.MicrometerConsumerListener;
import org.springframework.kafka.core.MicrometerProducerListener;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@EnableKafka
@Configuration
public class KafkaConsumerConfig {

  @Autowired
  private MeterRegistry meterRegistry;

  @Autowired
  private MetricPublisher metricPublisher;

  @Autowired
  private KafkaProperties kafkaProperties;

  @Autowired
  private KafkaRetryProperties kafkaRetryProperties;

  @Bean
  public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
    ConcurrentKafkaListenerContainerFactory<String, String> factory =
        new ConcurrentKafkaListenerContainerFactory<>();
    factory.setConsumerFactory(consumerFactory(kafkaProperties));
    factory.setConcurrency(kafkaProperties.getListener().getConcurrency());
    factory.setCommonErrorHandler(errorHandler(kafkaProperties));
    factory.getContainerProperties().setDeliveryAttemptHeader(true);
    return factory;
  }

  DefaultErrorHandler errorHandler(KafkaProperties kafkaProperties) {
    int interval = Integer.parseInt(
        kafkaProperties.getConsumer().getProperties().get(KafkaConstant.RETRY_INTERVAL_MS));
    int maxAttempts = Integer.parseInt(
        kafkaProperties.getConsumer().getProperties().get(KafkaConstant.RETRY_MAX_ATTEMPTS));
    FixedBackOff fixedBackOff = new FixedBackOff(interval, maxAttempts);
    DeadLetterPublishingRecoverer deadLetterPublishingRecoverer = new DeadLetterPublishingRecoverer(
        new KafkaTemplate<>(producerFactory(kafkaProperties)), this::getDltTopicPartition);
    return new DefaultErrorHandler(deadLetterPublishingRecoverer, fixedBackOff);
  }

  public ConsumerFactory<String, String> consumerFactory(KafkaProperties kafkaProperties) {
    DefaultKafkaConsumerFactory<String, String> factory = new DefaultKafkaConsumerFactory<>(
        kafkaProperties.buildConsumerProperties());
    factory.addListener(new MicrometerConsumerListener<>(meterRegistry));
    return factory;
  }

  public ProducerFactory<String, String> producerFactory(KafkaProperties kafkaProperties) {
    DefaultKafkaProducerFactory<String, String> factory = new DefaultKafkaProducerFactory<>(
        kafkaProperties.buildProducerProperties());
    factory.addListener(new MicrometerProducerListener<>(meterRegistry));
    return factory;
  }

  private TopicPartition getDltTopicPartition(ConsumerRecord<?, ?> r, Exception e) {

    boolean useOriginalTopic = false;
    if (e instanceof KafkaRetryException) {
      KafkaRetryException exception = (KafkaRetryException) e;
      useOriginalTopic = exception.isOriginalTopic();
    }

    String topicName = useOriginalTopic ? r.topic() : getTopicName(r.topic());
    int partition = useOriginalTopic ? r.partition() : getPartition(r.topic(), r.partition());

    log.error("publishing record to dead letter queue ",
        kv("topic", topicName),
        kv("valueSize", r.serializedValueSize()),
        kv("payload", r.value()),
        e
    );

    Tag topicNameTag = new ImmutableTag(MetricDimensionTags.KAFKA_TOPIC.getValue(),
        topicName);
    metricPublisher.incrementCounter(MetricType.KAFKA_DLT_MESSAGE, topicNameTag);
    return new TopicPartition(topicName, partition);
  }

  private String getTopicName(String originalTopic) {
    List<String> dltOriginalTopics = this.kafkaRetryProperties.getDltOriginalTopics();

    if (dltOriginalTopics.contains(originalTopic)) {
      return originalTopic + MessageConstant.DLT;
    }

    return originalTopic;
  }

  private Integer getPartition(String originalTopic, Integer originalPartition) {
    List<String> dltOriginalTopics = this.kafkaRetryProperties.getDltOriginalTopics();

    if (dltOriginalTopics.contains(originalTopic)) {
      return 0;
    }

    return originalPartition;
  }

}
