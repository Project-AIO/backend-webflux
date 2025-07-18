package com.idt.aiowebflux.dto;

import com.querydsl.core.annotations.QueryProjection;


public record RefData(
        Long answerId,
        String docId,
        float score,
        String extension,
        String path,
        String fileName,
        int totalPage,
        String revision,
        Long similarityDocId
) {
    @QueryProjection
    public RefData {
    }
}
