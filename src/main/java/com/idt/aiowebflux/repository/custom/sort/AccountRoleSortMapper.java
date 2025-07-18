package com.idt.aiowebflux.repository.custom.sort;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.idt.aiowebflux.entity.QAccount.account;
import static com.idt.aiowebflux.entity.QAccountRole.accountRole;

@Component
public class AccountRoleSortMapper implements Function<Sort.Order, OrderSpecifier<?>> {

    @Override
    public OrderSpecifier<?> apply(Sort.Order o) {
        return switch (o.getProperty()) {
            case "accountId"     -> o.isAscending()
                    ? account.accountId.asc()
                    : account.accountId.desc();
            case "roleName" -> o.isAscending()
                    ? account.name.asc()
                    : account.name.desc();
            case "updateDt" -> o.isAscending()
                    ? accountRole.updateDt.asc()
                    : accountRole.updateDt.desc();
            default -> throw DomainExceptionCode.SORTING_NOT_FOUND.newInstance("에러 정렬 조건 : "+ o.getProperty());
        };
    }
}
