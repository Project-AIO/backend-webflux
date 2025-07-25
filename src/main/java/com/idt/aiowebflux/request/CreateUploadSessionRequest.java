package com.idt.aiowebflux.request;

import com.idt.aiowebflux.entity.constant.AccessModifier;
import jakarta.persistence.Access;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateUploadSessionRequest(
        @NotNull AccessModifier accessModifier,
        @NotNull Long folderId,
        @NotNull List<FileMeta> files
) {
    public record FileMeta(
            @NotNull String clientFileId,
            @NotNull String filename,
            @Min(0) long size
            ) {
    }
}
