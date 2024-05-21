package com.ajaib.coin.market.maker.util;

import org.springframework.beans.BeanUtils;

public class BeanMapper {

  public static <T, S> T copy(S source, T target) {
    BeanUtils.copyProperties(source, target);
    return target;
  }

}
