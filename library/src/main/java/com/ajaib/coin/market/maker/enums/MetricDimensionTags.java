package com.ajaib.coin.market.maker.enums;

public enum MetricDimensionTags {
  KAFKA_TOPIC("topic"),
  ;

  private String value;

  MetricDimensionTags(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
