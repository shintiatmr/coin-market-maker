package com.ajaib.coin.market.maker.util;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.stereotype.Component;

@Component
public class BeanNonNullMapper extends BeanUtilsBean {

  @Override
  public void copyProperty(Object bean, String name, Object value)
      throws IllegalAccessException, InvocationTargetException {
    if (Objects.isNull(value)) return;
    super.copyProperty(bean, name, value);
  }

}
