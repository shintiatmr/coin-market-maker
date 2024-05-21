package com.ajaib.coin.market.maker.controller;

import com.ajaib.coin.market.maker.constant.ApiConstant;
import com.ajaib.coin.market.maker.dto.BaseResponse;
import com.ajaib.coin.market.maker.dto.SystemParameterRequest;
import com.ajaib.coin.market.maker.dto.SystemParameterResponse;
import com.ajaib.coin.market.maker.service.internal.InternalSystemParameterService;
import com.ajaib.coin.market.maker.util.ResponseHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SystemParameterController {

  @Autowired
  private InternalSystemParameterService systemParameterService;

  @PostMapping(ApiConstant.V1_INTERNAL_SYSTEM_PARAMETER)
  BaseResponse<SystemParameterResponse> upsert(
      @RequestBody @Validated SystemParameterRequest requestDto) {
    return ResponseHelper.ok(systemParameterService.upsert(requestDto));
  }

  @GetMapping(ApiConstant.V1_INTERNAL_SYSTEM_PARAMETER)
  BaseResponse<SystemParameterResponse> getAll() {
    return ResponseHelper.ok(systemParameterService.findAll());
  }

  @GetMapping(ApiConstant.V1_INTERNAL_SYSTEM_PARAMETER + "/{variable}")
  BaseResponse<SystemParameterResponse> getByVariable(@PathVariable String variable) {
    return ResponseHelper.ok(systemParameterService.findByVariable(variable));
  }

  @DeleteMapping(ApiConstant.V1_INTERNAL_SYSTEM_PARAMETER + "/{variable}")
  BaseResponse<SystemParameterResponse> deleteByVariable(@PathVariable String variable) {
    return ResponseHelper.ok(systemParameterService.deleteByVariable(variable));
  }
}
