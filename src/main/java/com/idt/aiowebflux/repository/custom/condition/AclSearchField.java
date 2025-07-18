package com.idt.aiowebflux.repository.custom.condition;

import com.idt.aiowebflux.entity.constant.ResourceType;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;

import java.util.Arrays;
import java.util.Locale;
import java.util.function.Function;

import static com.idt.aiowebflux.entity.QAclEntry.aclEntry;
import static com.idt.aiowebflux.entity.QAgent.agent;
import static com.idt.aiowebflux.entity.QAiModel.aiModel;
import static com.idt.aiowebflux.entity.QKnowledge.knowledge;
import static com.idt.aiowebflux.entity.QRole.role;

@Getter
public enum AclSearchField {

    NAME("roleName", input -> role.name.like("%" + input + "%")),

    RESOURCE_TYPE("resourceType", input -> aclEntry.id.resourceType.eq(ResourceType.valueOf(input.toUpperCase(Locale.ROOT)))),

    RESOURCE_NAME("resourceName", input -> agent.title.like("%" + input + "%")
            .or(knowledge.name.like("%" + input + "%"))
            .or(aiModel.name.like("%" + input + "%")));

    /* ───────── enum 본체 ───────── */

    private final String fieldName;
    private final Function<String, BooleanExpression> builder;

    /** 필드 주입용 생성자(필수) */
    AclSearchField(String fieldName,
                   Function<String, BooleanExpression> builder) {
        this.fieldName = fieldName;
        this.builder   = builder;
    }

    /** 실제 Predicate 생성 */
    public BooleanExpression build(String keyword) {
        return builder.apply(keyword);
    }

    /** fieldName → enum 상수 매핑 */
    public static AclSearchField fromString(final String searchField) {
        return Arrays.stream(values())
                .filter(f -> f.fieldName.equalsIgnoreCase(searchField))
                .findFirst()
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid search field: " + searchField));
    }
}
