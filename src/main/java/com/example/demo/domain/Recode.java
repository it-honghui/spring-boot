package com.example.demo.domain;

import lombok.Getter;

/**
 * @author honghui 2021/07/07
 */
@Getter
public enum Recode {

  SUCCESS(0, "ok"),

  NOT_FOUND(404, "未找到资源"),

  MISSING_PARAM(101, "缺少请求参数"),
  ACTION_EXCEPTION(102, "操作行为异常"),
  SERVER_BUSY(103, "服务器繁忙"),
  ;

  private final Integer code;
  private final String msg;

  Recode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
