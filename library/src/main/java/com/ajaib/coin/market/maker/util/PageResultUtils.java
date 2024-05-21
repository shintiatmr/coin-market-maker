package com.ajaib.coin.market.maker.util;

import com.ajaib.coin.market.maker.constant.ParamConstant;
import com.ajaib.coin.market.maker.dto.PageResultResponse;
import com.ajaib.coin.market.maker.dto.SimplePageRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageResultUtils {

  private static final String PARAM_FORMAT = "&%s=%s";

  private static <T> Boolean hasNext(List<T> data, Long count, SimplePageRequest simplePage) {
    int current = simplePage.getPageWithOffset() * simplePage.getPageSize() + data.size();
    return current < count;
  }

  private static <T> Boolean hasPrevious(SimplePageRequest simplePage) {
    return simplePage.getPage() > 1;
  }

  private static <T> String getNext(List<T> data, Long count, String urlParams,
      SimplePageRequest simplePage) {
    if (!hasNext(data, count, simplePage)) {
      return null;
    }

    return generateUrlPage(simplePage, 1) + urlParams;

  }

  private static String generateUrlPage(SimplePageRequest simplePage, int counter) {
    return String.format("?%s=%d&%s=%d", ParamConstant.PAGE, simplePage.getPage() + counter,
        ParamConstant.PAGE_SIZE, simplePage.getPageSize());
  }

  private static <T> String getPrevious(String urlParams, SimplePageRequest simplePage) {
    if (!hasPrevious(simplePage)) {
      return null;
    }

    return generateUrlPage(simplePage, -1) + urlParams;
  }

  public static <T> PageResultResponse<T> toPageResponse(List<T> data, Long count,
      SimplePageRequest simplePage) {
    return PageResultResponse.<T>builder()
        .count(count)
        .next(PageResultUtils.getNext(data, count, "", simplePage))
        .previous(PageResultUtils.getPrevious("", simplePage))
        .results(data)
        .build();
  }

  public static <T> PageResultResponse<T> toPageResponse(List<T> data, Long count,
      Map<String, String> queryParams) {
    Map<String, String> params = new HashMap<>(queryParams);

    int page = Integer.parseInt(params.remove("page"));
    int pageSize = Integer.parseInt(params.remove("page_size"));
    SimplePageRequest simplePage = SimplePageRequest.ofDefault(page, pageSize);

    String urlParams = params.keySet()
        .stream()
        .filter(ObjectUtils::isNotEmpty)
        .map(key -> String.format(PARAM_FORMAT, key, params.get(key)))
        .collect(Collectors.joining());

    return PageResultResponse.<T>builder()
        .count(count)
        .next(PageResultUtils.getNext(data, count, urlParams, simplePage))
        .previous(PageResultUtils.getPrevious(urlParams, simplePage))
        .results(data)
        .build();
  }
}
