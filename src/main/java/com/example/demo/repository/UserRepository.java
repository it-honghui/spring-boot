package com.example.demo.repository;

import com.example.demo.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author honghui 2021/07/07
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
