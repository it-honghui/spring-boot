package com.example.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author honghui 2022/3/14
 */
@Configuration
@ConfigurationProperties(prefix = "email")
public class CcEmail {

  private List<String> ccEmailList;

  public void setCcEmailList(List<String> ccEmailList) {
    this.ccEmailList = ccEmailList;
  }

  public List<String> getCcEmailList() {
    return ccEmailList;
  }

}
