package com.idt.aiowebflux.request;

import com.querydsl.core.annotations.QueryProjection;

public record DocumentFileDataRequest(
        String docId,
        String fileName,
        String extension,
        String revision
) {
    @QueryProjection
    public DocumentFileDataRequest {

    }
}
