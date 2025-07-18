package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.response.QResourceResponse;
import com.idt.aiowebflux.response.ResourceResponse;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QKnowledge.knowledge;

@RequiredArgsConstructor
@Repository
public class CustomKnowledgeRepositoryImpl implements CustomKnowledgeRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ResourceResponse> fetchAllKnowledgeByPage(Pageable pageable) {
        final long total = Optional.ofNullable(queryFactory
                        .select(knowledge.count())
                        .from(knowledge)
                        .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<ResourceResponse> results = queryFactory
                .select(new QResourceResponse(knowledge.knowledgeId, knowledge.name))
                .from(knowledge)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
