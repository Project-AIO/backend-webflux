package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.QPermissionDto;
import com.idt.aiowebflux.dto.QRolePermissionDto;
import com.idt.aiowebflux.dto.RolePermissionDto;
import com.idt.aiowebflux.repository.custom.condition.RolePermissionSearchField;
import com.idt.aiowebflux.repository.custom.sort.RolePermissionSortMapper;
import com.idt.aiowebflux.util.QuerydslOrderUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.idt.aiowebflux.entity.QPermission.permission;
import static com.idt.aiowebflux.entity.QRole.role;
import static com.idt.aiowebflux.entity.QRolePermission.rolePermission;

@RequiredArgsConstructor
@Repository
public class CustomRolePermissionRepositoryImpl implements CustomRolePermissionRepository{
    private final JPAQueryFactory queryFactory;
    private final QuerydslOrderUtil queryDslUtil;
    private final RolePermissionSortMapper rolePermissionSortMapper;
    @Override
    public Page<RolePermissionDto> findAllByPage(final Pageable pageable, final String searchField,final String searchInput) {
        final long total = Optional.ofNullable(queryFactory
                        .select(role.roleId.count())
                        .from(role)
                        .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<Long> pageIds = queryFactory
                .select(role.roleId)
                .distinct()                              // Shrinking 방지
                .from(role)
                .leftJoin(rolePermission).on(rolePermission.role.eq(role))
                .leftJoin(rolePermission.permission, permission)
                .where(containsSearchField(searchField, searchInput))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();


        final List<RolePermissionDto> results = queryFactory
                .selectFrom(role)
                .leftJoin(rolePermission).on(rolePermission.role.eq(role))
                .leftJoin(rolePermission.permission, permission)
                .where(role.roleId.in(pageIds))
                .orderBy(queryDslUtil.toOrderSpecifiers(pageable.getSort(), rolePermissionSortMapper))
                .transform(GroupBy.groupBy(role.roleId).list(
                        new QRolePermissionDto(
                                role.roleId,
                                role.name,
                                GroupBy.set(new QPermissionDto(
                                        permission.permissionId,
                                        permission.displayName,
                                        permission.description
                                ))
                        )
                ));

        return new PageImpl<>(results, pageable, total);
    }


    private BooleanExpression containsSearchField(final String searchField, final String searchInput) {
        if (searchField == null || searchField.isEmpty()) {
            return null;
        }

        final RolePermissionSearchField field = RolePermissionSearchField.fromString(searchField);
        return field.build(searchInput);
    }
}
