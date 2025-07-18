package com.idt.aiowebflux.request;

public record ConfigModelRequest(

        String embModelName,
        String rerkModelName,
        String aiModelName
) {
}
