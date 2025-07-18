package com.idt.aiowebflux.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface DictionaryRepository<T, K extends Number> extends JpaRepository<T, K> {
    List<T> findByKnowledge_KnowledgeId(final Long knowledgeId);

    Page<T> findByKnowledge_KnowledgeId(final Long knowledgeId, final Pageable pageable);

    boolean existsByMatchAndSource(final String match, final String source);
    void updateDictionaryById(final K id, final String source, final String match);
}
