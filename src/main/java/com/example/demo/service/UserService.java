package com.example.demo.service;

import com.example.demo.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author honghui 2021/07/07
 */
public interface UserService {

  User createUser(User user);

  Page<User> findUserList(Pageable pageable);
}
