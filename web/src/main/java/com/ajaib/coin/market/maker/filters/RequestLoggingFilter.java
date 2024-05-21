package com.ajaib.coin.market.maker.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

@Slf4j
public class RequestLoggingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {

    HttpServletRequest req = (HttpServletRequest) servletRequest;
    HttpServletResponse res = (HttpServletResponse) servletResponse;
    String query = ObjectUtils.defaultIfNull(req.getQueryString(), "");

    long startTime = System.currentTimeMillis();
    log.info("Start Request : {} {}?{}", req.getMethod(), req.getRequestURI(), query);
    filterChain.doFilter(servletRequest, servletResponse);

    double responseTime = (System.currentTimeMillis() - startTime) / 1000.0;
    log.info(
        "Finish Request : {} {}?{}, Response Status : {}, Response Time：{}ms",
        req.getMethod(), req.getRequestURI(), query, res.getStatus(), responseTime
    );


  }

}
