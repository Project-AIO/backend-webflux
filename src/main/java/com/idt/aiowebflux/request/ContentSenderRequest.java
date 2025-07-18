package com.idt.aiowebflux.request;

import com.idt.aiowebflux.dto.ModelDataDto;

public record ContentSenderRequest(
        String subscribeKey,
        String filePath,
        Float overlapTokenR,
        ModelDataDto embeddingModel,
        String docId
) {
}
