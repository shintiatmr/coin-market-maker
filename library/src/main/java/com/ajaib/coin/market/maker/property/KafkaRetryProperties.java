package com.ajaib.coin.market.maker.property;

import com.ajaib.coin.market.maker.constant.MessageConstant;
import java.util.Arrays;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "application.kafka.retry")
public class KafkaRetryProperties {

  private List<String> dltOriginalTopics = Arrays.asList(
      MessageConstant.TEST_EVENT
  );

}
