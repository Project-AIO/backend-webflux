package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.Homonym;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomonymRepository extends DictionaryRepository<Homonym, Long> {
    Page<Homonym> findByKnowledge_KnowledgeId(Long knowledgeId, Pageable pageable);

    List<Homonym> findByKnowledge_KnowledgeId(Long knowledgeId);
    @Modifying
    @Query("UPDATE Homonym h SET h.source = :source, h.match = :match WHERE h.homonymId = :homonymId")
    void updateDictionaryById(@Param("homonymId") final Long homonymId, @Param("source") final String source, @Param("match") final String match);
}
