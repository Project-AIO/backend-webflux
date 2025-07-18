package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.AclDto;
import com.idt.aiowebflux.dto.QAclDto;
import com.idt.aiowebflux.dto.QResourceAccessPermissionDto;
import com.idt.aiowebflux.entity.composite.AclEntryId;
import com.idt.aiowebflux.entity.constant.PrincipalType;
import com.idt.aiowebflux.entity.constant.ResourceType;
import com.idt.aiowebflux.repository.AgentRepository;
import com.idt.aiowebflux.repository.AiModelRepository;
import com.idt.aiowebflux.repository.KnowledgeRepository;
import com.idt.aiowebflux.repository.custom.condition.AclSearchField;
import com.idt.aiowebflux.repository.custom.sort.AclSortMapper;
import com.idt.aiowebflux.response.ResourceResponse;
import com.idt.aiowebflux.util.QuerydslOrderUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QAclEntry.aclEntry;
import static com.idt.aiowebflux.entity.QAgent.agent;
import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledge.knowledge;
import static com.idt.aiowebflux.entity.QRole.role;

@RequiredArgsConstructor
@Repository
public class CustomAclRepositoryImpl implements CustomAclRepository {
    private final JPAQueryFactory queryFactory;
    private final QuerydslOrderUtil queryDslUtil;
    private final AclSortMapper aclSortMapper;
    private final AiModelRepository aiModelRepository;
    private final KnowledgeRepository knowledgeRepository;
    private final AgentRepository agentRepository;

    @Override
    public Page<AclDto> findAllByPage(final Pageable pageable, final String searchField, final String searchInput) {
        final long total = Optional.ofNullable(queryFactory
                        .select(aclEntry.id.count())
                        .from(aclEntry)
                        .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<Long> roleIds = queryFactory.select(role.roleId)
                .from(role)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final List<AclEntryId> pageIds = queryFactory
                .select(aclEntry.id)
                .distinct()                              // Shrinking 방지
                .from(aclEntry)
                .leftJoin(agent).on(agent.agentId.eq(aclEntry.id.resourceId))
                .leftJoin(knowledge).on(knowledge.knowledgeId.eq(aclEntry.id.resourceId))
                .leftJoin(aiModel).on(aiModel.aiModelId.eq(aclEntry.id.resourceId))
                .leftJoin(role).on(role.roleId.eq(aclEntry.id.principalId)
                        .and(aclEntry.id.principalType.eq(PrincipalType.ROLE)))
                .where(containsSearchField(searchField, searchInput), role.roleId.in(roleIds)) // 검색 조건만 유지
                .fetch();

        final List<AclDto> queryResults = queryFactory
                .from(aclEntry)
                .leftJoin(agent).on(
                        agent.agentId.eq(aclEntry.id.resourceId)
                                .and(aclEntry.id.resourceType.eq(ResourceType.AGENT)))
                .leftJoin(aiModel).on(
                        aiModel.aiModelId.eq(aclEntry.id.resourceId)
                                .and(aclEntry.id.resourceType.eq(ResourceType.MODEL)))
                .leftJoin(knowledge).on(
                        knowledge.knowledgeId.eq(aclEntry.id.resourceId)
                                .and(aclEntry.id.resourceType.eq(ResourceType.KNOWLEDGE)))
                .leftJoin(role).on(role.roleId.eq(aclEntry.id.principalId)
                        .and(aclEntry.id.principalType.eq(PrincipalType.ROLE)))
                .where(aclEntry.id.in(pageIds))
                .orderBy(queryDslUtil.toOrderSpecifiers(
                        pageable.getSort(), aclSortMapper))
                .orderBy(aclEntry.id.resourceType.asc())
                .transform(
                        GroupBy.groupBy(aclEntry.id.principalId)      // principalId만 key
                                .list(
                                        new QAclDto(
                                                Expressions.constant(PrincipalType.ROLE),
                                                aclEntry.id.principalId,
                                                role.name,
                                                GroupBy.map(
                                                        aclEntry.id.resourceType,
                                                        GroupBy.list(
                                                                new QResourceAccessPermissionDto(
                                                                        aclEntry.id.resourceId,
                                                                        aclEntry.id.resourceType,
                                                                        Expressions.cases()
                                                                                .when(agent.agentId.isNotNull()).then(agent.title)
                                                                                .when(aiModel.aiModelId.isNotNull()).then(aiModel.name)
                                                                                .otherwise(knowledge.name),
                                                                        aclEntry.permMask))))));

        return new PageImpl<>(queryResults, pageable, total);
    }

    @Override
    public Page<ResourceResponse> findByResourceType(final Pageable pageable,
                                                     final ResourceType resourceType) {

        return switch (resourceType) {
            case MODEL -> aiModelRepository.fetchAllAiModelsByPage(pageable);
            case KNOWLEDGE -> knowledgeRepository.fetchAllKnowledgeByPage(pageable);
            case AGENT -> agentRepository.fetchAllAgentsByPage(pageable);
        };
    }

    private BooleanExpression containsSearchField(final String searchField, final String searchInput) {
        if (searchField == null || searchField.isEmpty()) {
            return null;
        }

        final AclSearchField field = AclSearchField.fromString(searchField);
        return field.build(searchInput);
    }
}

