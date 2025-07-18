package com.idt.aiowebflux.response.core;

import com.querydsl.core.annotations.QueryProjection;

public record CoreDocFileData(
        String docId,
        String extension,
        String path,
        String fileName,
        int totalPage,
        String revision
) {
    @QueryProjection
    public CoreDocFileData {

    }
}
