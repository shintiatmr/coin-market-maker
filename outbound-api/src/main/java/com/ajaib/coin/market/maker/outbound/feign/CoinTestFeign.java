package com.ajaib.coin.market.maker.outbound.feign;

import com.ajaib.coin.market.maker.outbound.feign.config.AjaibFeignConfig;
import com.ajaib.coin.market.maker.outbound.feign.fallback.CoinTestFallback;
import com.ajaib.coin.market.maker.constant.HeaderConstant;
import com.ajaib.coin.market.maker.dto.BaseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(value = "coinTestFeign",
    url = "${application.coin-test.api.host}",
    fallbackFactory = CoinTestFallback.class,
    configuration = AjaibFeignConfig.class)
public interface CoinTestFeign {

  @GetMapping("api/v1/coin-test/test")
  BaseResponse<Boolean> get(
      @RequestHeader(HeaderConstant.USER_ID) Long ajaibId
  );

}
