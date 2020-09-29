package com.lhstack.microservice.uaa.controller;

import com.lhstack.microservice.common.exceptions.ExistResourceException;
import com.lhstack.microservice.common.resp.Result;
import com.lhstack.microservice.common.resp.ResultEnum;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @class: ExceptionControllerAdvice
 * @author: lhstack
 * @date:2020/9/27
 * @description:
 * @since: 1.8
 **/
@RestControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(ExistResourceException.class)
    public ResponseEntity<Result> existResourceExceptionHandler(ExistResourceException e){
        return ResponseEntity.ok(Result.failed(ResultEnum.VALIDATED_ERROR.setMsg(e.getMessage())));
    }
}
