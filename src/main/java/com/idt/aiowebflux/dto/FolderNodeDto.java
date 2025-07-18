package com.idt.aiowebflux.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class FolderNodeDto{
    private final Long folderId;
    private final String name;
    private int childCount;
    private final List<FolderNodeDto> children;
    private final List<ParentNodeDto> parent;

    public FolderNodeDto(final Long folderId, final String name, final int childCount, final List<FolderNodeDto> children,
                         final List<ParentNodeDto> parent) {
        this.folderId = folderId;
        this.name = name;
        this.childCount = childCount;
        this.children = children;
        this.parent = parent;
    }

    public void childCount(final int childCount) {
        this.childCount = childCount;
    }
}
