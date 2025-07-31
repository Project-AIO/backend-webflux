package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.constant.AccessModifier;

import java.nio.file.Path;

public record DocumentMetaData(
        String accountId,
        Long folderId,
        Path path,
        String fileName,
        Long totalBytes,
        String uploadId,
        AccessModifier accessModifier
) {
}
