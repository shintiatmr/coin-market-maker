package com.ajaib.coin.market.maker.outbound.impl.kafka;

import com.ajaib.coin.market.maker.constant.MessageConstant;
import com.ajaib.coin.market.maker.outbound.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class EventPublisherImpl implements EventPublisher {

  @Autowired
  private KafkaTemplate<String, String> kafkaTemplate;

  @Override
  public void publish(String message) {
    this.kafkaTemplate.send(MessageConstant.TEST_EVENT, message);
  }

}
