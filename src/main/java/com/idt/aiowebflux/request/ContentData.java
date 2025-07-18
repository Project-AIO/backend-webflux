package com.idt.aiowebflux.request;

public record ContentData(
        int startPage,
        int endPage,
        String title
) {
}
