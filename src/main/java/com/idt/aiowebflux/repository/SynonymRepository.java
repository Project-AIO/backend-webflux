package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Synonym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Long>, DictionaryRepository<Synonym, Long> {
    Page<Synonym> findByKnowledge_KnowledgeId(Long knowledgeId, Pageable pageable);

    List<Synonym> findByKnowledge_KnowledgeId(Long knowledgeId);

    @Modifying
    @Query("UPDATE Synonym s SET s.source = :source, s.match = :match WHERE s.synonymId = :synonymId")
    void updateDictionaryById(@Param("synonymId") final Long synonymId, @Param("source") final String source, @Param("match") final String match);

}
