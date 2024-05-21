package com.ajaib.coin.market.maker.service.impl.internal;

import com.ajaib.coin.market.maker.dto.SystemParameterRequest;
import com.ajaib.coin.market.maker.dto.SystemParameterResponse;
import com.ajaib.coin.market.maker.entity.SystemParameter;
import com.ajaib.coin.market.maker.service.SystemParameterService;
import com.ajaib.coin.market.maker.service.impl.converter.SystemParameterConverter;
import com.ajaib.coin.market.maker.service.internal.InternalSystemParameterService;
import com.ajaib.coin.market.maker.util.BeanMapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InternalSystemParameterServiceImpl implements InternalSystemParameterService {

  private final SystemParameterConverter converter;
  private final SystemParameterService systemParameterService;

  @Override
  public List<SystemParameterResponse> findAll() {
    return this.converter.to(this.systemParameterService.findAll());
  }

  @Override
  public SystemParameterResponse findByVariable(String variable) {
    return this.converter.to(this.systemParameterService.findByVariable(variable));
  }

  @Override
  public SystemParameterResponse upsert(SystemParameterRequest systemParameter) {
    SystemParameter entity = BeanMapper.copy(systemParameter, new SystemParameter());
    return this.converter.to(this.systemParameterService.upsert(entity));
  }

  @Override
  public SystemParameterResponse deleteByVariable(String variable) {
    return this.converter.to(this.systemParameterService.deleteByVariable(variable));
  }

}