package com.ajaib.coin.market.maker.outbound.feign.fallback;

import com.ajaib.coin.market.maker.outbound.feign.CoinTestFeign;
import com.ajaib.coin.market.maker.dto.BaseResponse;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CoinTestFallback implements FallbackFactory<CoinTestFeign> {

  @Override
  public CoinTestFeign create(Throwable throwable) {
    return new CoinTestFeign() {
      @Override
      public BaseResponse<Boolean> get(Long ajaibId) {
        log.error("CoinTestFeign failed to get", throwable);

        return BaseResponse.<Boolean>builder()
            .result(true)
            .build();
      }
    };
  }
}
