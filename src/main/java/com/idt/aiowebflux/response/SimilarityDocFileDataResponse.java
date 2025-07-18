package com.idt.aiowebflux.response;

import com.querydsl.core.annotations.QueryProjection;

public record SimilarityDocFileDataResponse(
        Long similarityDocId,
        String docId,
        Float score,
        String extension,
        String path,
        String fileName,
        int totalPage,
        String revision
) {
    @QueryProjection
    public SimilarityDocFileDataResponse {

    }
}
