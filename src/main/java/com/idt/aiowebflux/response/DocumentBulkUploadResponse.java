package com.idt.aiowebflux.response;

import com.idt.aiowebflux.entity.constant.State;

import java.time.LocalDateTime;

public record DocumentBulkUploadResponse(
        String uploadId,
        String originalFileName,
        String revision,
        State state,
        Integer totalPage,
        String dataMbSize, //MB 단위로 저장됨
        LocalDateTime uploadDateTime,
        String creatorId,
        String creatorName,
        Integer completedCount,
        Integer failedCount
) {
}
