package com.idt.aiowebflux.dto;

import com.idt.aiowebflux.entity.Folder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter

@AllArgsConstructor
@NoArgsConstructor
public class FolderDto {
    private Long folderId;
    private Long parentId;
    private String name;
    private LocalDateTime createdDt;

    public static List<FolderDto> from(final List<Folder> folders) {
        return folders.stream()
                .map(e -> new FolderDto(
                        e.getFolderId(),
                        e.getParent().getFolderId(),
                        e.getName(),
                        e.getCreateDt())
                )
                .toList();
    }
}
