package com.ajaib.coin.market.maker.property;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "application.cache")
public class CacheProperties {

  private List<CacheNames> cacheNames = new ArrayList<>();

  @Data
  public static class CacheNames {
    private String key;
    private Duration expiryTtl;
  }

}
