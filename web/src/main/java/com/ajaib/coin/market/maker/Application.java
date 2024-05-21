package com.ajaib.coin.market.maker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.ajaib.coin.market.maker"})
@EntityScan(basePackages = {"com.ajaib.coin.market.maker"})
@EnableJpaRepositories(basePackages = {"com.ajaib.coin.market.maker"})
@SpringBootApplication
public class Application {

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

}
