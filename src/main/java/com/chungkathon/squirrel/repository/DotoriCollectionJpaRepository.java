package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.DotoriCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DotoriCollectionJpaRepository extends JpaRepository<DotoriCollection, Long> {
//    @Override
    Optional<DotoriCollection> findById(Long Long);
    @Query("SELECT d FROM DotoriCollection d WHERE d.deleted = false")
    List<DotoriCollection> findAllActiveDotoriCollections();
}
