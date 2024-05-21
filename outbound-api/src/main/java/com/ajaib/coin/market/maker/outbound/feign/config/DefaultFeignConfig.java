package com.ajaib.coin.market.maker.outbound.feign.config;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import feign.hystrix.SetterFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DefaultFeignConfig {

  @Bean
  public SetterFactory setterFactory() {
    return (target, method) -> HystrixCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(target.name()))
        .andCommandKey(HystrixCommandKey.Factory.asKey(target.name()));
  }
}
