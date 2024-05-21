package com.ajaib.coin.market.maker.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI openApi(@Value("${application.version}") String version) {
    return new OpenAPI().info(new Info().title("Coin Market Maker Service").version(version));
  }

}
