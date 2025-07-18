package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record DocumentDatasetResponse(
        //파일 경로
        String docId,
        String sourceName,
        String fileName,
        Integer totalPages,
        State state,
        Integer progress,
        LocalDateTime uploadDate,
        long fileByteSize

) {
    @QueryProjection
    public DocumentDatasetResponse {
    }
}
