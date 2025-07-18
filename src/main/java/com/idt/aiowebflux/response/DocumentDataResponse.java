package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;
import com.querydsl.core.annotations.QueryProjection;

import java.time.LocalDateTime;

public record DocumentDataResponse(
        String docId,
        Long folderId,
        State state,
        Integer progress,
        DocumentFileData fileData,
        LocalDateTime uploadDt
) {
    @QueryProjection
    public DocumentDataResponse {
    }

}
