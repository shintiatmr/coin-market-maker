package com.ajaib.coin.market.maker.message;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class AjaibEventMessage {

  private Long targetUserId;
  private String eventName;
  private String moduleName;
  private Message message;
  private Cta cta;

  @Setter
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Message {

    private String title;
    private String body;
  }

  @Setter
  @Getter
  @Builder
  @AllArgsConstructor
  @NoArgsConstructor
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class Cta {

    private String action;
    private List<String> actionParams;
    private ActionPayload actionPayload;
    private Boolean autoRead;
    private Boolean sendPushNotification;
  }

  @Setter
  @Getter
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public static class ActionPayload {

  }
}
