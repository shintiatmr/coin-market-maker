package com.ajaib.coin.market.maker.outbound.feign.config;

import com.ajaib.coin.market.maker.exception.AjaibFeignException;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.form.FormEncoder;
import feign.hystrix.SetterFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.ResponseEntityDecoder;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

public class AjaibFeignConfig {

  @Autowired
  private ObjectFactory<HttpMessageConverters> messageConverters;

  @Bean
  public SetterFactory setterFactory() {
    return (target, method) -> HystrixCommand.Setter
        .withGroupKey(HystrixCommandGroupKey.Factory.asKey(target.name()))
        .andCommandKey(HystrixCommandKey.Factory.asKey(target.name()));
  }

  @Bean
  Encoder feignFormEncoder() {
    return new FormEncoder(new SpringEncoder(this.messageConverters));
  }

  @Bean
  public Decoder springDecoder() {
    return new ResponseEntityDecoder(new SpringDecoder(messageConverters));
  }

  @Bean
  public ErrorDecoder ajaibErrorDecoder(Decoder decoder) {
    return (methodKey, response) -> {
      try {
        AjaibFeignException exception = (AjaibFeignException) decoder
            .decode(response, AjaibFeignException.class);
        exception.setStatus(HttpStatus.valueOf(response.status()));
        exception.setSuccess(HttpStatus.OK.value() == response.status());
        return exception;
      } catch (Exception e) {
        return AjaibFeignException.builder()
            .status(HttpStatus.valueOf(response.status()))
            .success(false)
            .build();
      }
    };
  }
}