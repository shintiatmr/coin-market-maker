package com.ajaib.coin.market.maker.service.internal;

import com.ajaib.coin.market.maker.dto.SystemParameterRequest;
import com.ajaib.coin.market.maker.dto.SystemParameterResponse;
import java.util.List;

public interface InternalSystemParameterService {

  List<SystemParameterResponse> findAll();

  SystemParameterResponse findByVariable(String variable);

  SystemParameterResponse upsert(SystemParameterRequest systemParameter);

  SystemParameterResponse deleteByVariable(String variable);

}
