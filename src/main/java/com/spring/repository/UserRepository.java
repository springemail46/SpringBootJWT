package com.spring.repository;

import com.spring.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository  extends JpaRepository<UserDao, Long> {
    UserDao findByUsername(String username);
}
