package com.idt.aiowebflux.repository.custom.sort;

import com.idt.aiowebflux.exception.DomainExceptionCode;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.function.Function;

import static com.idt.aiowebflux.entity.QRole.role;

@Component
public class AclSortMapper implements Function<Sort.Order, OrderSpecifier<?>> {

    @Override
    public OrderSpecifier<?> apply(Sort.Order o) {
        return switch (o.getProperty()) {
            case "roleName" -> o.isAscending()
                    ? role.name.asc()
                    : role.name.desc();
            default -> throw DomainExceptionCode.SORTING_NOT_FOUND.newInstance("에러 정렬 조건 : "+ o.getProperty());
        };
    }
}
