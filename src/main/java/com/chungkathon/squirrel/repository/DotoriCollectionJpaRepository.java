package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.DotoriCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface DotoriCollectionJpaRepository extends JpaRepository<DotoriCollection, Long> {
    Optional<DotoriCollection> findById(Long Long);
    @Query("SELECT d FROM DotoriCollection d WHERE d.member.urlRnd = :urlRnd AND d.deleted = false")
    List<DotoriCollection> findActiveDotoriCollectionsByMember(@Param("urlRnd") String urlRnd);
}
