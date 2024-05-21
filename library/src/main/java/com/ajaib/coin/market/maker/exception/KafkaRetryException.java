package com.ajaib.coin.market.maker.exception;

import lombok.Getter;

public class KafkaRetryException extends RuntimeException {

  @Getter
  private final boolean originalTopic;

  public KafkaRetryException(Throwable cause, boolean originalTopic) {
    super(cause);
    this.originalTopic = originalTopic;
  }

}
