package com.idt.aiowebflux.repository.custom.condition;

import static com.idt.aiowebflux.entity.QAccount.account;
import static com.idt.aiowebflux.entity.QAccountRole.accountRole;

import com.idt.aiowebflux.dto.SearchParam;
import com.idt.aiowebflux.entity.constant.Status;
import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;
import lombok.Getter;


@Getter
public enum AccountRoleSearchField {
    ACCOUNT_ID("accountId", p ->
            account.accountId.like("%" + p.keyword() + "%")),

    ROLE_NAME("roleName", p ->
            accountRole.role.name.like("%" + p.keyword() + "%")),
    NAME("roleName", p ->
            account.name.like("%" + p.keyword() + "%")),
    STATUS("status", p -> {
        if (p.keyword() == null || p.keyword().isEmpty()) {
            return null;
        }
        return account.status.eq(Status.valueOf(p.keyword().toUpperCase(Locale.ROOT)));
    }),

    UPDATE_DT("updateDt", p -> {
        if (p.from() == null || p.to() == null) {
            throw DomainExceptionCode.DATE_SEARCH_INVALID
                    .newInstance("날짜 범위가 비었습니다.");
        }
        return accountRole.updateDt.between(
                p.from().atStartOfDay(),
                p.to().plusDays(1).atStartOfDay());   // inclusive
    });

    private final String fieldName;
    private final Function<SearchParam, BooleanExpression> builder;

    AccountRoleSearchField(String fieldName, Function<SearchParam, BooleanExpression> builder) {
        this.fieldName = fieldName;
        this.builder = builder;
    }

    public static AccountRoleSearchField fromString(final String searchField) {
        return Arrays.stream(values())
                .filter(f -> f.fieldName.equalsIgnoreCase(searchField))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid search field: " + searchField));
    }

    public BooleanExpression build(final SearchParam param) {
        return builder.apply(param);
    }
}
