package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.User;
import java.util.Optional;

public interface UserRepository {
    // 중복 가입 확인을 위함
    Optional<User> findByEmail(String email);
}
