package com.idt.aiowebflux.util;

import com.querydsl.core.types.OrderSpecifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@Slf4j
@Component
public class QuerydslOrderUtil {

    public OrderSpecifier<?>[] toOrderSpecifiers(final Sort sort, final Function<Sort.Order, OrderSpecifier<?>> orderMapper) {
        return sort.stream()
                .map(orderMapper)
                .filter(Objects::nonNull)          // 허용되지 않은 컬럼은 무시
                .toArray(OrderSpecifier[]::new);
    }
}
