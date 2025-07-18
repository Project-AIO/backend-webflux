package com.idt.aiowebflux.entity.constant.sort;

import com.idt.aiowebflux.exception.DomainExceptionCode;

import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 무조건 paging 조회에서 이걸 사용하는 게 아니라
 * 이걸 사용하는 조건은
 * <p>
 * 1. querydsl에서 paging을 사용할 때 !
 * <p>
 * JPQL에서 제공하는 인터페이스로 Pageable만 넘겨서 사용할 때는 굳이 사용할 필요가 없이 JPQL에서 제공하는 걸 사용하면 됨
 */
public sealed interface SortProperty
        permits ProjectAccountProperty, ConversationProperty, DocumentProperty /* , AnotherProperty */ {

    String key();

    String path();

    ConcurrentMap<Class<?>, Map<String, ? extends SortProperty>> CACHE =
            new ConcurrentHashMap<>();

    @SuppressWarnings("unchecked")
    static <E extends Enum<E> & SortProperty>
    Map<String, E> enumMap(Class<E> type) {
        return (Map<String, E>) CACHE.computeIfAbsent(type, ignore ->
                // 여기서 'type'을 사용해야 제네릭 E[] 로 추론됩니다
                Arrays.stream(type.getEnumConstants())
                        .collect(Collectors.toUnmodifiableMap(
                                p -> p.key().toLowerCase(Locale.ROOT),
                                Function.identity()
                        ))
        );
    }


    static <E extends Enum<E> & SortProperty>
    E of(Class<E> type, String rawKey) {
        if (rawKey == null) {
            throw DomainExceptionCode.SORT_PROPERTY_NOT_FOUND.newInstance();
        }
        return Optional.ofNullable(enumMap(type).get(rawKey.toLowerCase(Locale.ROOT)))
                .orElseThrow(DomainExceptionCode.SORT_PROPERTY_NOT_FOUND::newInstance);
    }

    static <E extends Enum<E> & SortProperty>
    String pathOf(Class<E> type, String rawKey) {
        return of(type, rawKey).path();
    }
}