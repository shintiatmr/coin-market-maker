package com.ajaib.coin.market.maker.exception;

import com.ajaib.coin.market.maker.type.ErrorType;
import lombok.Getter;

public class ServerException extends BaseException {

  @Getter
  private ErrorType errorType;

  public ServerException() {
    super(ErrorType.INTERNAL_SERVER_ERROR.getDescription());
    this.errorType = ErrorType.INTERNAL_SERVER_ERROR;
  }

  public ServerException(ErrorType errorType) {
    super(errorType.getDescription());
    this.errorType = errorType;
  }

  public ServerException(ErrorType errorType, String message) {
    super(message);
    this.errorType = errorType;
  }
}
