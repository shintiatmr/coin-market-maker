package com.ajaib.coin.market.maker.enums;

public enum MetricType {
  KAFKA_DLT_MESSAGE("kafka.dlt.message"),
  KAFKA_ERRORS("kafka.error"),
  ;

  private String value;

  MetricType(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}