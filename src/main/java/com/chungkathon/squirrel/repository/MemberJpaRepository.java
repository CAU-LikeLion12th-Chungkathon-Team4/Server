package com.chungkathon.squirrel.repository;


import com.chungkathon.squirrel.domain.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;


@Repository
public interface MemberJpaRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    List<Member> findAllByUsername(String username);

    // Username 중복 검사 쿼리
    boolean existsByUsername(String username);

    // URL 난수를 이용하여 사용자의 정보를 조회하기
    Optional<Member> findByUrlRnd(String urlRnd);
}