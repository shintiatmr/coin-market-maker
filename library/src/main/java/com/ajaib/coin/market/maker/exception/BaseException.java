package com.ajaib.coin.market.maker.exception;

import com.ajaib.coin.market.maker.type.ErrorType;
import lombok.Getter;

public class BaseException extends RuntimeException {

  @Getter
  protected ErrorType errorType;

  public BaseException(String message) {
    super(message);
    this.errorType = ErrorType.INTERNAL_SERVER_ERROR;
  }

  public BaseException(ErrorType errorType) {
    super(errorType.getDescription());
    this.errorType = ErrorType.INTERNAL_SERVER_ERROR;
  }
}
