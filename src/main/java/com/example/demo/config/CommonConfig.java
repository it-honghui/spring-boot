package com.example.demo.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.List;

/**
 * @author honghui 2022/3/10
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "rpc")
public class CommonConfig {

  private List<RpcInfo> rpcInfoList;

  public List<RpcInfo> getRpcInfoList() {
    return rpcInfoList;
  }

  public void setRpcInfoList(List<RpcInfo> rpcInfoList) {
    this.rpcInfoList = rpcInfoList;
  }

  @PostConstruct
  public void init() {
    log.info("rpcInfoList:{}", rpcInfoList.toString());
  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder(toBuilder = true)
  public static class RpcInfo implements Serializable {

    private static final long serialVersionUID = -3299279522988797731L;
    private String name;
    private String url;
    private Integer chainId;
    private String symbol;
    private String browser;
  }

}
