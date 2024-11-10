package com.chungkathon.squirrel.repository;

import org.hibernate.boot.archive.internal.JarProtocolArchiveDescriptor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizJpaRrepository extends JpaRepository {
    QuizJpaRrepository findQuizJpaRrepository();
}
