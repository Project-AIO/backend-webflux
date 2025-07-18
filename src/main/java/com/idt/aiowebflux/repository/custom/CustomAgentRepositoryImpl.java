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

import static com.idt.aiowebflux.entity.QAgent.agent;

@RequiredArgsConstructor
@Repository
public class CustomAgentRepositoryImpl implements CustomAgentRepository{
    private final JPAQueryFactory queryFactory;
    @Override
    public Page<ResourceResponse> fetchAllAgentsByPage(final Pageable pageable) {
        final long total = Optional.ofNullable(queryFactory
                        .select(agent.count())
                        .from(agent)
                        .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<ResourceResponse> results = queryFactory
                .select(new QResourceResponse(agent.agentId, agent.title))
                .from(agent)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(results, pageable, total);
    }
}
