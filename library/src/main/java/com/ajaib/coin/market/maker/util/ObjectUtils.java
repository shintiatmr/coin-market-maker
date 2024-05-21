package com.ajaib.coin.market.maker.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public class ObjectUtils {

  public static <T> boolean isEmpty(T... data) {
    return Arrays.stream(data)
        .allMatch(ObjectUtils::isEmpty);
  }

  public static <T> boolean isEmpty(T object) {
    if (object instanceof Collection) {
      return CollectionUtils.isEmpty((Collection<?>) object);
    }
    if (object instanceof String) {
      return StringUtils.isEmpty(String.valueOf(object));
    }
    return Objects.isNull(object);
  }

  public static <T> boolean isNotEmpty(T object) {
    return !isEmpty(object);
  }

  public static <T> T getOrDefault(T object, T defaultValue) {
    return isEmpty(object) ? defaultValue : object;
  }

}
