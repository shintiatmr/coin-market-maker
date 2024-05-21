package com.ajaib.coin.market.maker.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class SimplePageRequest {

  private final Integer PAGE_OFFSET = 1;

  private Integer page;
  private Integer pageSize;
  private Sort sort;

  public Integer getPageWithOffset() {
    return page - PAGE_OFFSET;
  }

  public static SimplePageRequest ofDefault(int page, int pageSize) {
    return SimplePageRequest.builder()
        .page(page)
        .pageSize(pageSize)
        .sort(Sort.by(Sort.Direction.DESC, "created_at"))
        .build();
  }

  public static SimplePageRequest withSort(int page, int pageSize, Sort sort) {
    return SimplePageRequest.builder()
        .page(page)
        .pageSize(pageSize)
        .sort(sort)
        .build();
  }

  public PageRequest getPageRequest() {
    return PageRequest.of(this.getPageWithOffset(), this.getPageSize(), this.getSort());
  }

  public OrderBy getFirstOrderBy() {
    return this.sort
        .stream()
        .map(order -> OrderBy.builder()
            .property(order.getProperty())
            .direction(order.getDirection().name())
            .build())
        .findFirst()
        .orElse(OrderBy.builder()
            .property("created_at")
            .direction(Sort.Direction.DESC.name())
            .build());
  }

  @Data
  @Builder
  public static class OrderBy {

    private String property;
    private String direction;
  }

}
