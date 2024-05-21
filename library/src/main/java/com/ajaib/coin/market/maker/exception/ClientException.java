package com.ajaib.coin.market.maker.exception;

import com.ajaib.coin.market.maker.type.ErrorType;

public class ClientException extends BaseException {

  public ClientException() {
    super(ErrorType.INTERNAL_SERVER_ERROR.getDescription());
  }

  public ClientException(ErrorType errorType) {
    super(errorType);
    this.errorType = errorType;
  }

  public ClientException(ErrorType errorType, Object... args) {
    super(String.format(errorType.getDescription(), args));
    this.errorType = errorType;
  }
}
