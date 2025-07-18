package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.SimilarityDocPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarityDocPageRepository extends JpaRepository<SimilarityDocPage, Long> {
    List<SimilarityDocPage> findBySimilarityDoc_SimilarityDocIdIn(final List<Long> similarityDocIds);
}
