package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author honghui 2022/3/10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MerkleProofDTO implements Serializable {

  private static final long serialVersionUID = 1052575118498704399L;
  private String merkleRoot;
  private String tokenTotal;
  private Map<String, ProofInfo> claims;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder(toBuilder = true)
  public static class ProofInfo implements Serializable {

    private static final long serialVersionUID = 1004516306703997589L;
    private Integer index;
    private String amount;
    private List<String> proof;
  }

}
