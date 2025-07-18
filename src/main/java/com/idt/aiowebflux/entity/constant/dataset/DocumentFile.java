package com.idt.aiowebflux.entity.constant.dataset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DocumentFile {
    SOURCE_NAME("sourceName"), FILE_NAME("fileName"), TOTAL_PAGES("pages"), FILE_SIZE("fileSize"), STATE("state");

    private final String value;
}
