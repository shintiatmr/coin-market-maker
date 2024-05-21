package com.ajaib.coin.market.maker.config;


import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.ajaib.coin.market.maker.outbound.feign")
public class FeignConfig {

}
