package com.idt.aiowebflux.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class SseSubscribeUuidUtil {
    private final String docUuidKey;
    private final String docBulkUuidKey;

    public SseSubscribeUuidUtil(@Value("${sse.doc.subscription.key}") final String docUuidKey,
                                @Value("${sse.doc.subscription.bulk.key}") final String docBulkUuidKey) {
        this.docUuidKey = docUuidKey;
        this.docBulkUuidKey = docBulkUuidKey;
    }

    public String generateDeterministicUUID(final String accountId) {
        final String combinedKey = accountId + docUuidKey;
        return UUID.nameUUIDFromBytes(combinedKey.getBytes(StandardCharsets.UTF_8)).toString();
    }

    public String generateBulkDeterministicUUID(final String accountId) {
        final String combinedKey = accountId + docBulkUuidKey;
        return UUID.nameUUIDFromBytes(combinedKey.getBytes(StandardCharsets.UTF_8)).toString();
    }
}
