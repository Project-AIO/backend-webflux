package com.idt.aiowebflux.dto;

import lombok.Getter;

@Getter
public class ParentNodeDto {
    private final Long folderId;
    private final String name;

    public ParentNodeDto(final Long folderId, final String name) {
        this.folderId = folderId;
        this.name = name;
    }
}
