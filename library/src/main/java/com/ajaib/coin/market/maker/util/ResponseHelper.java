package com.ajaib.coin.market.maker.util;

import com.ajaib.coin.market.maker.dto.BaseResponse;
import com.ajaib.coin.market.maker.type.ErrorType;
import java.util.List;

public class ResponseHelper {

  public static <T> BaseResponse<T> ok(T data) {
    BaseResponse<T> response = new BaseResponse<>();
    response.setErrCode(ErrorType.APPROVED_OK.getValue());
    response.setErrMessage(ErrorType.APPROVED_OK.getDescription());
    response.setResult(data);
    return response;
  }

  public static <T> BaseResponse<T> ok(List<T> data) {
    BaseResponse<T> response = new BaseResponse<>();
    response.setErrCode(ErrorType.APPROVED_OK.getValue());
    response.setErrMessage(ErrorType.APPROVED_OK.getDescription());
    response.setResults(data);
    return response;
  }

  public static <T> BaseResponse<T> ok() {
    BaseResponse<T> response = new BaseResponse<>();
    response.setErrCode(ErrorType.APPROVED_OK.getValue());
    response.setErrMessage(ErrorType.APPROVED_OK.getDescription());
    return response;
  }

  public static <T> BaseResponse<T> error(Throwable throwable) {
    BaseResponse<T> response = new BaseResponse<>();
    response.setErrCode(ErrorType.REST_CONTROLLER_ERROR.getValue());
    response.setErrMessage(throwable.getMessage());
    return response;
  }

  public static <T> BaseResponse<T> error(ErrorType txnError) {
    BaseResponse<T> response = new BaseResponse<>();
    response.setErrCode(txnError.getValue());
    response.setErrMessage(txnError.getDescription());
    return response;
  }


}