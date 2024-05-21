package com.ajaib.coin.market.maker.util;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class DateTimeUtils {

  private DateTimeUtils() {}

  public static ZonedDateTime ofEpochMilli(Long millis) {
    return ZonedDateTime.ofInstant(Instant.ofEpochMilli(millis), ZoneId.systemDefault());
  }

}
