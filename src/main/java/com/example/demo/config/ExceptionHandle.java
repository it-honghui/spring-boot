package com.example.demo.config;

import com.example.demo.domain.ERR;
import com.example.demo.domain.R;
import com.example.demo.domain.Recode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author honghui 2021/07/07
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

  @ExceptionHandler(value = ERR.class)
  @ResponseBody
  public R<?> handle(ERR e) {
    log.error(e.toStr());
    e.printStackTrace();
    return R.error(e.getCode(), e.getMessage());
  }

  @ExceptionHandler(value = MethodArgumentNotValidException.class)
  @ResponseBody
  public R<?> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
    log.error(e.getMessage());
    List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
    String message = allErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
        .collect(Collectors.joining(";"));
    return R.error(Recode.REGULAR_EXPRESSION_VALIDATION_FAILED.getCode(), message);
  }

}
