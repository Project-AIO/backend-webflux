package com.idt.aiowebflux.entity.constant.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * querydsl에서 sorting property로 사용하기 위해서 아래와 같이 정의함
 */
@Getter
@RequiredArgsConstructor
public enum ProjectAccountProperty implements SortProperty {
    PROJECT_ID("project_id", "projectId"),
    ACCOUNT_ID("account_id", "account.accountId"),
    ROLE("role", "account.role"),
    NAME("roleName", "account.roleName"),
    ADMIN_ID("admin_id", "account.admin.accountId");

    private final String key;
    private final String path;  // 인터페이스의 path()

    @Override
    public String key() {
        return key;
    }

    @Override
    public String path() {
        return path;
    }
}