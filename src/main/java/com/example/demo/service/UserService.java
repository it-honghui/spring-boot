package com.example.demo.service;

import com.example.demo.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * @author honghui 2021/07/07
 */
public interface UserService {

  User createUser(User user);

  Page<User> findUserList(Pageable pageable);

  List<User> findUserList();

  Optional<User> findUser(Long id);

  List<User> findUserQuery(String username, String password);

  void updateUsername(Long id, String username);
}
