package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizJpaRepository extends JpaRepository<Quiz, Long> {
//    Optional<Quiz> findById(Long quiz_id);
}