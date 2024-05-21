package com.ajaib.coin.market.maker.service.impl;

import com.ajaib.coin.market.maker.constant.CacheConstant;
import com.ajaib.coin.market.maker.entity.SystemParameter;
import com.ajaib.coin.market.maker.exception.ClientException;
import com.ajaib.coin.market.maker.repository.SystemParameterRepository;
import com.ajaib.coin.market.maker.service.SystemParameterService;
import com.ajaib.coin.market.maker.type.ErrorType;
import com.ajaib.coin.market.maker.util.MapperHelper;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

  @Autowired
  private SystemParameterRepository systemParameterRepository;

  @Autowired
  private MapperHelper mapperHelper;

  @Override
  public List<SystemParameter> findAll() {
    return this.systemParameterRepository.findAll();
  }

  @Override
  @Cacheable(cacheNames = CacheConstant.FIND_SYSTEM_PARAMETER_BY_VARIABLE, key = "#variable")
  public SystemParameter findByVariable(String variable) {
    return this.systemParameterRepository.findFirstByVariableAndDeletedAtIsNull(variable)
        .orElseThrow(() -> new ClientException(ErrorType.COIN_MARKET_MAKER_SYSTEM_PARAMETER_NOT_FOUND));
  }

  @Override
  @CachePut(cacheNames = CacheConstant.FIND_SYSTEM_PARAMETER_BY_VARIABLE, key = "#systemParameter.variable")
  public SystemParameter upsert(SystemParameter systemParameter) {
    SystemParameter exiting = this.systemParameterRepository
        .findFirstByVariableAndDeletedAtIsNull(systemParameter.getVariable())
        .orElse(new SystemParameter());

    this.mapperHelper.mapNonNullBean(systemParameter, exiting);
    return this.systemParameterRepository.save(exiting);
  }

  @Override
  @CacheEvict(cacheNames = CacheConstant.FIND_SYSTEM_PARAMETER_BY_VARIABLE, key = "#variable")
  public SystemParameter deleteByVariable(String variable) {
    SystemParameter existing = this.findByVariable(variable);
    existing.setDeletedAt(ZonedDateTime.now());

    return this.systemParameterRepository.save(existing);
  }
}
