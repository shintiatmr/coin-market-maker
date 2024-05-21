package com.ajaib.coin.market.maker.advice;

import static net.logstash.logback.argument.StructuredArguments.kv;
import com.ajaib.coin.market.maker.dto.BaseResponse;
import com.ajaib.coin.market.maker.exception.AjaibFeignException;
import com.ajaib.coin.market.maker.exception.ClientException;
import com.ajaib.coin.market.maker.exception.ServerException;
import com.ajaib.coin.market.maker.type.ErrorType;
import java.util.Objects;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ClientException.class)
  public BaseResponse clientException(ClientException exception, HttpServletResponse response) {
    log.error("CLIENT_EXCEPTION error: {}", exception.getMessage(), exception);
    return BaseResponse.builder()
        .errCode(exception.getErrorType().getValue())
        .errMessage(exception.getErrorType().getDescription())
        .build();
  }

  @ExceptionHandler(AjaibFeignException.class)
  public BaseResponse feignAjaibClientError(AjaibFeignException exception,
      HttpServletResponse response) {
    log.error("FEIGN_AJAIB_CLIENT_EXCEPTION", kv("errorMessage", exception.getErrMessage()),
        exception);
    if (exception.getStatus().is4xxClientError()) {
      response.setStatus(HttpStatus.BAD_REQUEST.value());
    } else {
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
    return BaseResponse.builder()
        .errCode(exception.getErrCode())
        .errMessage(exception.getErrMessage())
        .build();
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(ServerException.class)
  public BaseResponse serverException(ServerException exception, HttpServletResponse response) {
    log.error("SERVER_EXCEPTION error: {}", exception.getMessage(), exception);
    return BaseResponse.builder()
        .errCode(exception.getErrorType().getValue())
        .errMessage(exception.getErrorType().getDescription())
        .build();
  }


  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public BaseResponse badRequest(MethodArgumentNotValidException exception,
      HttpServletResponse response) {
    log.error("BAD_REQUEST error: {}", exception.getMessage(), exception);
    return BaseResponse.builder()
        .errCode(ErrorType.REST_CONTROLLER_ERROR.getValue())
        .errMessage(ErrorType.REST_CONTROLLER_ERROR.getDescription())
        .build();
  }

  @ExceptionHandler(Throwable.class)
  public BaseResponse internalServerError(Throwable throwable, HttpServletResponse response) {
    if (Objects.nonNull(throwable.getCause())
        && throwable.getCause() instanceof AjaibFeignException) {
      return feignAjaibClientError((AjaibFeignException) throwable.getCause(), response);
    }
    log.error("INTERNAL_SERVER_ERROR error: {}", throwable.getMessage(), throwable);
    response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
    return BaseResponse.builder()
        .errCode(ErrorType.INTERNAL_SERVER_ERROR.getValue())
        .errMessage(ErrorType.INTERNAL_SERVER_ERROR.getDescription())
        .build();
  }

}
