package com.idt.aiowebflux.repository;

import com.idt.aiowebflux.dto.KnowledgeDto;
import com.idt.aiowebflux.entity.Knowledge;
import com.idt.aiowebflux.repository.custom.CustomKnowledgeRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KnowledgeRepository extends JpaRepository<Knowledge, Long>, CustomKnowledgeRepository {
    boolean existsByName(String name);

    //account랑 fetch join
    @Query("SELECT new com.idt.aiowebflux.dto.KnowledgeDto(k.knowledgeId, k.name, k.description, k.account.accountId, k.account.name, k.createDt, k.updateDt) " +
            "FROM Knowledge k JOIN k.account a ")
    List<KnowledgeDto> findAllAsDto();
}
