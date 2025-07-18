package com.idt.aiowebflux.repository.custom;

import com.idt.aiowebflux.dto.AccountRoleDto;
import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.dto.QAccountRoleDto;
import com.idt.aiowebflux.dto.QRoleDto;
import com.idt.aiowebflux.dto.RoleDto;
import com.idt.aiowebflux.dto.SearchParam;
import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.constant.Status;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.custom.condition.AccountRoleSearchField;
import com.idt.aiowebflux.repository.custom.sort.AccountRoleSortMapper;
import com.idt.aiowebflux.util.QuerydslOrderUtil;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static com.idt.aiowebflux.entity.QAccount.account;
import static com.idt.aiowebflux.entity.QAccountRole.accountRole;
import static com.idt.aiowebflux.entity.QRole.role;

@RequiredArgsConstructor
@Repository
public class CustomAccountRoleRepositoryImpl implements CustomAccountRoleRepository {
    private final JPAQueryFactory queryFactory;
    private final QuerydslOrderUtil queryDslUtil;
    private final AccountRoleSortMapper accountRoleSortMapper;

    @Override
    public Page<AccountRoleDto> findAllByPage(final Pageable pageable,
                                              final String searchField,
                                              final SearchParam searchParam,
                                              final Status status
                                              ) {
        final long total = Optional.ofNullable(queryFactory
                        .select(account.accountId.count())
                        .from(account)
                        .where(statusWhereCondition(status))
                        .fetchOne())
                .orElse(0L);

        if (total == 0) {
            return Page.empty(pageable);
        }

        final List<String> pageIds = queryFactory
                .select(account.accountId)
                .distinct()                              // Shrinking 방지
                .from(account)
                .leftJoin(accountRole).on(accountRole.account.eq(account))
                .leftJoin(accountRole.role, role)
                .where(containsSearchField(searchField, searchParam),
                        statusWhereCondition(status))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        final DateTimeExpression<LocalDateTime> latestUpdate =
                Expressions.dateTimeTemplate(LocalDateTime.class,
                        "GREATEST(COALESCE({0}, '1000-01-01'), COALESCE({1}, '1000-01-01'))",
                        accountRole.updateDt, account.updateDt);




        List<AccountRoleDto> results = queryFactory
                .selectFrom(account)
                .leftJoin(accountRole).on(accountRole.account.eq(account))
                .leftJoin(accountRole.role, role)
                .where(account.accountId.in(pageIds))
                .orderBy(queryDslUtil.toOrderSpecifiers(pageable.getSort(), accountRoleSortMapper))
                .transform(GroupBy.groupBy(account.accountId).list(
                        new QAccountRoleDto(
                                account.accountId,
                                account.name,
                                account.status,
                                GroupBy.list(
                                        new QRoleDto(
                                                role.roleId,
                                                role.name
                                        )
                                ),
                                GroupBy.max(latestUpdate)
                        )
                ));

        results = results.stream()
                .map(accountRole -> {
                    final List<RoleDto> roleDtos = accountRole.roles().stream()
                            .filter(r -> r.roleId() != null)
                            .toList();


                    return new AccountRoleDto(
                            accountRole.accountId(),
                            accountRole.name(),
                            accountRole.status(),
                            roleDtos,
                            accountRole.updateDt()
                    );
                })
                .toList();

        return new PageImpl<>(results, pageable, total);
    }

    @Override
    public AccountSecret findAccountSecretById(String accountId) {
        final Account user = queryFactory
                .select(account)
                .from(account)
                .where(account.accountId.eq(accountId))
                .fetchOne();

        if (user == null) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance("사용자를 찾을 수 없습니다. accountId: " + accountId);
        }

        if(user.getStatus().equals(Status.DELETED)) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance("삭제된 계정입니다. accountId: " + accountId);
        }

        if(user.getStatus().equals(Status.INACTIVE)){
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance("비활성화된 계정입니다. accountId: " + accountId);
        }

        final Set<String> roleNames = new HashSet<>(queryFactory
                .select(role.name)
                .from(accountRole)
                .leftJoin(accountRole.role, role)
                .where(accountRole.account.accountId.eq(accountId))
                .fetch());

        return new AccountSecret(user.getAccountId(), user.getPw(), roleNames);

    }

    private BooleanExpression containsSearchField(  final String searchField, final SearchParam searchParam) {
        if (searchField == null || searchField.isEmpty()) {
            return null;
        }

        final AccountRoleSearchField field = AccountRoleSearchField.fromString(searchField);

        return field.build(searchParam);
    }
    private BooleanExpression statusWhereCondition(final Status status) {
        return status.equals(Status.ALL) ? Expressions.TRUE : account.status.eq(status);
    }

}
