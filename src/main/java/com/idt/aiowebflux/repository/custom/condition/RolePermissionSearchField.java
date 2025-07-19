package com.idt.aiowebflux.repository.custom.condition;

import static com.idt.aiowebflux.entity.QPermission.permission;
import static com.idt.aiowebflux.entity.QRole.role;

import com.querydsl.core.types.dsl.BooleanExpression;
import java.util.Arrays;
import java.util.function.Function;
import lombok.Getter;

@Getter
public enum RolePermissionSearchField {

    ROLE_NAME("roleName", input -> role.name.like("%" + input + "%")),

    DISPLAY_NAME("displayName", input -> permission.displayName.like("%" + input + "%")),
    ;

    /* ───────── enum 본체 ───────── */

    private final String fieldName;
    private final Function<String, BooleanExpression> builder;

    /**
     * 필드 주입용 생성자(필수)
     */
    RolePermissionSearchField(String fieldName,
                              Function<String, BooleanExpression> builder) {
        this.fieldName = fieldName;
        this.builder = builder;
    }

    /**
     * fieldName → enum 상수 매핑
     */
    public static RolePermissionSearchField fromString(final String searchField) {
        return Arrays.stream(values())
                .filter(f -> f.fieldName.equalsIgnoreCase(searchField))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid search field: " + searchField));
    }

    /**
     * 실제 Predicate 생성
     */
    public BooleanExpression build(String keyword) {
        return builder.apply(keyword);
    }
}
