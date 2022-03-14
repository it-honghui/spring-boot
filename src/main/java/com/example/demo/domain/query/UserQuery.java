package com.example.demo.domain.query;

import cn.hutool.core.util.StrUtil;
import com.example.demo.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author honghui 2022/3/14
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserQuery {

  private String username;
  private String password;

  public Specification<User> toSpec() {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();
      if (StrUtil.isNotBlank(username)) {
        predicates.add(criteriaBuilder.equal(root.get("username"), username));
      }
      if (StrUtil.isNotBlank(password)) {
        predicates.add(criteriaBuilder.equal(root.get("password"), password));
      }
      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }

}
