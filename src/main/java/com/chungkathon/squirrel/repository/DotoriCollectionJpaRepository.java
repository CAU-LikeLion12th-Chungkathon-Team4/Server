package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.DotoriCollection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DotoriCollectionJpaRepository extends JpaRepository<DotoriCollection, Long> {
//    @Override
    Optional<DotoriCollection> findById(Long aLong);
}
