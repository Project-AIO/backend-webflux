package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.entity.KnowledgeConfig;
import com.idt.aiowebflux.repository.custom.CustomKnowledgeConfigRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KnowledgeConfigRepository extends JpaRepository<KnowledgeConfig, Long>, CustomKnowledgeConfigRepository {

    Optional<KnowledgeConfig> findByKnowledge_KnowledgeId(final Long knowledgeId);

    @Query("SELECT ck.overlapTokenR FROM KnowledgeConfig ck WHERE ck.knowledge.knowledgeId = :knowledgeId")
    Optional<Float> findOverlapTokenRateByKnowledgeId(final Long knowledgeId);

}
