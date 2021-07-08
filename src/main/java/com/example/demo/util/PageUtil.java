package com.example.demo.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * @author honghui 2021/07/07
 */
public class PageUtil {

  private final static int MIN_SIZE = 0;
  private final static int MAX_SIZE = 100;

  public static PageRequest of(int page, int size) {
    return of(page, size, null, null);
  }

  public static PageRequest of(int page, int size, String sortField, Direction sortDirection) {
    page = Math.max(0, page);
    size = Math.max(MIN_SIZE, Math.min(size, MAX_SIZE));
    if (StringUtils.isEmpty(sortField) || StringUtils.isEmpty(sortDirection)) {
      return PageRequest.of(page, size);
    } else {
      return PageRequest.of(page, size, sortDirection, sortField);
    }
  }

  public static <T> List<T> toPage(List<T> totalList, Pageable pageable) {
    int page = pageable.getPageNumber();
    int size = pageable.getPageSize();
    int from = Math.min(totalList.size(), page * size);
    int to = Math.min(totalList.size(), (page + 1) * size);
    return totalList.subList(from, to);
  }
}
