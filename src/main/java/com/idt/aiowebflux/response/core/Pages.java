package com.idt.aiowebflux.response.core;

import com.querydsl.core.annotations.QueryProjection;

public record Pages(
        Integer page,
        Float leftX,
        Float rightX,
        Float topY,
        Float bottomY
) {
    @QueryProjection
    public Pages {
    }
}
