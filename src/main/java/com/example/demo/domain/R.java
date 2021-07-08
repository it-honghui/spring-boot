package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

/**
 * @author honghui 2021/07/07
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class R<T> {

  /**
   * 错误码
   */
  private Integer code;

  /**
   * 提示信息
   */
  private String msg;

  /**
   * 具体内容
   */
  private T data;

  public static <T> R<T> ok(T t) {
    return R.<T>builder()
        .code(Recode.SUCCESS.getCode())
        .msg(Recode.SUCCESS.getMsg())
        .data(t).build();
  }

  public static <D> R<P<D>> ok(Page<D> page) {
    return R.ok(P.of(page));
  }

  public static R error(Recode err) {
    return R.builder()
        .code(err.getCode())
        .msg(err.getMsg()).build();
  }

  public static R error(Integer code, String msg) {
    return R.builder()
        .code(code)
        .msg(msg).build();
  }
}
