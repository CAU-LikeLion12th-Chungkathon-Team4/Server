package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 중복 가입 확인을 위함
    Optional<User> findByEmail(String email);
}
