package com.ajaib.coin.market.maker.inbound;

public interface BaseListener {

  void onReceive(String json, Integer deliveryAttempt, String topic);

}
