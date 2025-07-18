package com.idt.aiowebflux.dto;

import lombok.AccessLevel;

public record DocumentsMetaData(
        String subscriptionKey,
        String tempDocIdentifier,
        String originalFileName,
        byte[] fileBytes,
        String accountId,
        Long folderId,
        Integer totalPage,
        AccessLevel accessLevel,
        Integer totalFileCount,
        Integer failedCount,
        Integer completedCount
) {
}
