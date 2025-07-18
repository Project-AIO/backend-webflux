package com.idt.aiowebflux.response.core;

import com.querydsl.core.annotations.QueryProjection;

import java.util.List;

public record Ref(
        String docId,
        List<Pages> pages,
        float score,
        String extension,
        String path,
        String fileName,
        int totalPage,
        String revision
) {
    @QueryProjection
    public Ref {
    }
}
