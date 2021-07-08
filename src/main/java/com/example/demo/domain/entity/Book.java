package com.example.demo.domain.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author honghui 2021/07/07
 */
@Data
@Builder
public class Book {

  private Long id;
  private String name;
  private Integer price;
}
