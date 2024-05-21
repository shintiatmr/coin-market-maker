package com.ajaib.coin.market.maker.service;

import com.ajaib.coin.market.maker.entity.SystemParameter;
import java.util.List;

public interface SystemParameterService {

  List<SystemParameter> findAll();

  SystemParameter findByVariable(String variable);

  SystemParameter upsert(SystemParameter systemParameter);

  SystemParameter deleteByVariable(String variable);

}
