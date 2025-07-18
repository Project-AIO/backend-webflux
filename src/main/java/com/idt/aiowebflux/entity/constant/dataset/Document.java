package com.idt.aiowebflux.entity.constant.dataset;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Document {
    UPLOAD_DATE("uploadDate");
    private final String value;
}
