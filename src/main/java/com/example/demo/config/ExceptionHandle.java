package com.example.demo.config;

import com.example.demo.domain.ERR;
import com.example.demo.domain.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
