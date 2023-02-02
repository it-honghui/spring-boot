package com.example.demo.domain.dto;

import com.example.demo.constant.Constant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * @author honghui 2023/02/02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class LoginDto implements Serializable {

  private static final long serialVersionUID = -819065011234426404L;

  @Pattern(regexp = Constant.ADDRESS_PATTERN, message = "address should match /" + Constant.ADDRESS_PATTERN + "/")
  @ApiModelProperty("进行签名的账号地址")
  private String address;

  @ApiModelProperty("1-CRITICAL 2-HIGH 3-MEDIUM 4-LOW")
  @Range(min = 1, max = 4, message = "level should match ^[1-4]d*$ (1-CRITICAL 2-HIGH 3-MEDIUM 4-LOW)")
  private Integer level;

}

