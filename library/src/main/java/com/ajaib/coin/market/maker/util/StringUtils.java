package com.ajaib.coin.market.maker.util;

import java.util.Collections;
import java.util.Map;
import org.apache.commons.lang.text.StrSubstitutor;

public class StringUtils {

  public static String replace(String content, Map<String, String> replacement) {
    return StrSubstitutor.replace(content, replacement, "{", "}");
  }

  public static String replace(String content, String key, String value) {
    return replace(content, Collections.singletonMap(key, value));
  }

}
