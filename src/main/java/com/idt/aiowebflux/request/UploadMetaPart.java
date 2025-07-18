package com.idt.aiowebflux.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record UploadMetaPart(
        @NotNull List<FileEntry> files
) {
    public record FileEntry(
            @NotBlank String filename,
            @NotBlank String serverFileId
    ) {}

    /** filename -> serverFileId 맵 생성 (중복 filename이면 마지막 값이 남음). */
    public Map<String,String> toFilenameMap() {
        return files.stream()
                .collect(Collectors.toMap(FileEntry::filename, FileEntry::serverFileId, (a, b) -> b));
    }
    /** serverFileId -> filename 맵이 필요한 경우 */
    public Map<String,String> toServerFileIdMap() {
        return files.stream()
                .collect(Collectors.toMap(FileEntry::serverFileId, FileEntry::filename, (a,b) -> b));
    }
}
