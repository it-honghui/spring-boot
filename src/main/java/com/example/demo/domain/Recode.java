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
  PASSWORD_ERROR(104, "密码错误"),
  UPLOAD_FILE_CANNOT_BE_EMPTY(105, "上传文件不能为空"),
  FILE_UPLOAD_TYPE_NOT_ALLOWED(106, "上传文件类型不被允许"),
  FILE_TOO_LARGE(107, "文件过大"),
  FILE_NAME_TOO_LONG(108, "文件名称过长"),
  FAIL_RENDER_MAIL(109, "邮件模板读取失败"),
  ;

  private final Integer code;
  private final String msg;

  Recode(Integer code, String msg) {
    this.code = code;
    this.msg = msg;
  }
}
