package com.ajaib.coin.market.maker.filters;

import com.ajaib.coin.market.maker.constant.ApiConstant;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<RequestLoggingFilter> loggingFilter(){
    FilterRegistrationBean<RequestLoggingFilter> registrationBean
        = new FilterRegistrationBean<>();

    registrationBean.setFilter(new RequestLoggingFilter());
    registrationBean.addUrlPatterns(
        ApiConstant.V1_ROOT + "/*",
        ApiConstant.V1_ROOT_INTERNAL + "/*"
    );
    registrationBean.setOrder(2);

    return registrationBean;
  }

}
