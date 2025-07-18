package com.idt.aiowebflux.entity.constant.sort;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ConversationProperty implements SortProperty {
    CONVERSATION_ID("conversation_id", "conversationId"),
    CLIENT_ID("client_id", "client.clientId"),
    CREATE_DT("create_dt", "createDt"),
    DONE_DT("used_dt", "usedDt");

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
