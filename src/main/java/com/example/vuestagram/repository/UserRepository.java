package com.example.vuestagram.repository;


import com.example.vuestagram.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 추상 메소드 정의
    Optional<User> findByAccount(String account);
}
