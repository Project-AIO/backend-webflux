package com.idt.aiowebflux.repository.custom;

import static com.idt.aiowebflux.entity.QAccount.account;
import static com.idt.aiowebflux.entity.QAccountRole.accountRole;
import static com.idt.aiowebflux.entity.QRole.role;

import com.idt.aiowebflux.dto.AccountSecret;
import com.idt.aiowebflux.dto.SearchParam;
import com.idt.aiowebflux.entity.Account;
import com.idt.aiowebflux.entity.constant.Status;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.idt.aiowebflux.repository.custom.condition.AccountRoleSearchField;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashSet;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CustomAccountRoleRepositoryImpl implements CustomAccountRoleRepository {
    private final JPAQueryFactory queryFactory;


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

        if (user.getStatus().equals(Status.DELETED)) {
            throw DomainExceptionCode.ACCOUNT_NOT_FOUND.newInstance("삭제된 계정입니다. accountId: " + accountId);
        }

        if (user.getStatus().equals(Status.INACTIVE)) {
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

    private BooleanExpression containsSearchField(final String searchField, final SearchParam searchParam) {
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
