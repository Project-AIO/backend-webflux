package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.entity.AiModel;
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

import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledgeAiModel.knowledgeAiModel;

@RequiredArgsConstructor
@Repository
public class CustomAiModelRepositoryImpl implements CustomAiModelRepository {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<AiModel> findByKnowledgeId(final Long knowledgeId) {
        return queryFactory
                .select(aiModel)
                .from(aiModel)
                .join(knowledgeAiModel).on(knowledgeAiModel.aiModel.aiModelId.eq(aiModel.aiModelId))
                .where(knowledgeAiModel.knowledge.knowledgeId.eq(knowledgeId))
                .fetch();
    }

    @Override
    public Page<ResourceResponse> fetchAllAiModelsByPage(final Pageable pageable) {
        final long total = Optional.ofNullable(queryFactory
                .select(aiModel.count())
                .from(aiModel)
                .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<ResourceResponse> results = queryFactory
                .select(new QResourceResponse(aiModel.aiModelId, aiModel.name))
                .from(aiModel)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }

}
