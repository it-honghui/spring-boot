package com.example.demo.domain;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author honghui 2021/07/07
 */
@Data
public class P<T> {

  private List<T> content;

  private long totalElements;
  private long totalPages;
  private int page;
  private int size;

  private Boolean isFirstPage;
  private Boolean isLastPage;
  private Boolean isEmpty;

  public P(Page<T> page) {
    this.content = page.getContent();

    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.page = page.getNumber();
    this.size = page.getSize();

    this.isFirstPage = page.isFirst();
    this.isLastPage = page.isLast();
    this.isEmpty = page.isEmpty();
  }

  public static <T> P<T> of(Page<T> page) {
    return new P<>(page);
  }
}
