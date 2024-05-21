package com.ajaib.coin.market.maker.service.impl.converter;

import com.ajaib.coin.market.maker.converter.BaseConverter;
import com.ajaib.coin.market.maker.dto.SystemParameterResponse;
import com.ajaib.coin.market.maker.entity.SystemParameter;
import com.ajaib.coin.market.maker.util.BeanMapper;
import org.springframework.stereotype.Component;

@Component
public class SystemParameterConverter extends
    BaseConverter<SystemParameter, SystemParameterResponse> {

  @Override
  public SystemParameter of(SystemParameterResponse object) {
    return BeanMapper.copy(object, new SystemParameter());
  }

  @Override
  public SystemParameterResponse to(SystemParameter object) {
    return BeanMapper.copy(object, new SystemParameterResponse());
  }

}