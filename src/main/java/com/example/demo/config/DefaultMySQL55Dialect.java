package com.example.demo.config;

import org.hibernate.dialect.MySQL5Dialect;

/**
 * @author honghui 2022/3/10
 */
public class DefaultMySQL55Dialect extends MySQL5Dialect {

  @Override
  public String  getTableTypeString() {
    return " ENGINE=InnoDB DEFAULT CHARSET=utf8mb4";
  }

}
