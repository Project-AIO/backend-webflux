package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.SimilarityDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SimilarityDocRepository extends JpaRepository<SimilarityDoc, Long> {

    List<SimilarityDoc> getSimilarityDocsByDocument_DocId(final String docId);

    SimilarityDoc getSimilarityDocsBySimilarityDocId(final Long similarityDocId);

    void deleteSimilarityDocByDocument_DocId(final String docId);
}
