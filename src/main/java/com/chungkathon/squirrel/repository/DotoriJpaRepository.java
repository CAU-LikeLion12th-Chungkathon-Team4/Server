package com.chungkathon.squirrel.repository;

import com.chungkathon.squirrel.domain.Dotori;
import com.chungkathon.squirrel.domain.DotoriCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DotoriJpaRepository extends JpaRepository<Dotori, Long>  {
    Optional<Dotori> findById(Long id);

    @Query("SELECT d FROM Dotori d WHERE d.dotoriCollection = :dotoriCollection AND d.deleted = false")
    List<Dotori> findAllActiveDotori(DotoriCollection dotoriCollection);
}
