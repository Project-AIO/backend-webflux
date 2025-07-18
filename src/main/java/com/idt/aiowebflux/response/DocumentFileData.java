package com.idt.aiowebflux.response;

import com.querydsl.core.annotations.QueryProjection;

public record DocumentFileData(
        Long docFileId,
        String extension,
        String path,
        String fileName,
        int totalPage,
        String revision
) {
    @QueryProjection
    public DocumentFileData {
    }
}
