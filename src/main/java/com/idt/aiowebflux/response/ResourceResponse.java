package com.idt.aiowebflux.response;

import com.querydsl.core.annotations.QueryProjection;

public record ResourceResponse(
        Long resourceId,
        String resourceName
) {
    @QueryProjection
    public ResourceResponse {
    }
}
