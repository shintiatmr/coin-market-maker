package com.ajaib.coin.market.maker.util;

import static net.logstash.logback.argument.StructuredArguments.kv;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.dozermapper.core.Mapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class MapperHelper {

  private final Mapper dozerMapper;
  private final ObjectMapper objectMapper;
  private final BeanNonNullMapper beanNonNullMapper;

  public <T> T mapBean(String source, Class<T> tClass) {
    try {
      return this.objectMapper.readValue(source, tClass);
    } catch (JsonProcessingException e) {
      log.error("Error when MapperHelper#mapBean ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }

  public <T> T mapBean(Object source, TypeReference<T> typeReference) {
    try {
      return this.objectMapper.readValue(toString(source), typeReference);
    } catch (JsonProcessingException e) {
      log.error("Error when MapperHelper#mapBean ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }

  public <T> T mapBean(Object source, Class<T> tClass) {
    try {
      return this.dozerMapper.map(source, tClass);
    } catch (Exception e) {
      log.error("Error when MapperHelper#mapBean ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }

  public <T> T mapBean(Object source, T target) {
    try {
      this.dozerMapper.map(source, target);
      return target;
    } catch (Exception e) {
      log.error("Error when MapperHelper#mapBean ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }

  public <T> T mapNonNullBean(Object source, T target) {
    try {
      this.beanNonNullMapper.copyProperties(target, source);
      return target;
    } catch (Exception e) {
      log.error("Error when MapperHelper#mapNonNullBean ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }

  public String toString(Object source) {
    try {
      if (source instanceof String) {
        return String.valueOf(source);
      }
      return this.objectMapper.writeValueAsString(source);
    } catch (JsonProcessingException e) {
      log.error("Error when MapperHelper#toString ", kv("source", source),
          kv("errorMessage", e.getMessage()), e);
      return null;
    }
  }
}
