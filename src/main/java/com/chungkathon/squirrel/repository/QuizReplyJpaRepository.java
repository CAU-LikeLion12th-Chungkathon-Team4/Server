package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.QuizReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizReplyJpaRepository extends JpaRepository<QuizReply, Long> {
}